package com.hasalp.ctoulel_finance_service.service;

import com.hasalp.ctoulel_finance_service.client.TenantServiceClient;
import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentStatusResponseDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.model.PaymentProvider;
import com.hasalp.ctoulel_finance_service.model.PaymentStatus;
import com.hasalp.ctoulel_finance_service.model.PaymentTransaction;
import com.hasalp.ctoulel_finance_service.model.Versement;
import com.hasalp.ctoulel_finance_service.paydunya.PayDunyaClient;
import com.hasalp.ctoulel_finance_service.paydunya.PayDunyaDisburseClient;
import com.hasalp.ctoulel_finance_service.paydunya.PayDunyaProperties;
import com.hasalp.ctoulel_finance_service.paydunya.dto.*;
import com.hasalp.ctoulel_finance_service.repository.PaymentTransactionRepository;
import com.hasalp.ctoulel_finance_service.repository.VersementRepository;
import com.hasalp.ctoulel_finance_service.security.TenantContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Year;
import java.util.HexFormat;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository transactionRepository;
    private final VersementRepository versementRepository;
    private final PayDunyaClient payDunyaClient;
    private final PayDunyaDisburseClient payDunyaDisburseClient;
    private final PayDunyaProperties payDunyaProperties;
    private final TenantServiceClient tenantServiceClient;

    @Override
    @Transactional
    public PaymentInitiateResponseDTO initiate(PaymentInitiateRequestDTO dto) {

        UUID tenantId = TenantContext.get();
        if (tenantId == null) {
            throw new BusinessException("Tenant introuvable pour cette requete");
        }

        PayDunyaInvoiceRequest invoiceRequest = new PayDunyaInvoiceRequest(
                new PayDunyaInvoiceRequest.Invoice(dto.montant(), "Versement " + dto.mois()),
                new PayDunyaInvoiceRequest.Store(payDunyaProperties.getStoreName()),
                new PayDunyaInvoiceRequest.Actions(
                        payDunyaProperties.getCallbackUrl(),
                        payDunyaProperties.getReturnUrl(),
                        payDunyaProperties.getCancelUrl()
                ),
                Map.of(
                        "membreId", dto.membreId().toString(),
                        "mois", dto.mois(),
                        "tenantId", tenantId.toString()
                )
        );

        PayDunyaInvoiceResponse invoiceResponse = payDunyaClient.createInvoice(invoiceRequest);
        if (!invoiceResponse.isSuccess()) {
            throw new BusinessException("Creation de la facture PayDunya echouee : " + invoiceResponse.response_text());
        }

        String invoiceToken = invoiceResponse.token();
        String checkoutUrl;
        String omUrl = null;
        String maxitUrl = null;

        if (payDunyaProperties.isSandbox()) {
            // PayDunya n'expose pas les routes par operateur (wave-senegal, new-orange-money-senegal)
            // en sandbox : il faut passer par le compte de test generique du dashboard.
            PayDunyaSoftpayResponse softpay = payDunyaClient.payWithSandboxTestAccount(new PayDunyaSandboxSoftpayRequest(
                    payDunyaProperties.getSandboxTestPhone(),
                    payDunyaProperties.getSandboxTestEmail(),
                    payDunyaProperties.getSandboxTestPassword(),
                    invoiceToken
            ));
            if (!softpay.isSuccess()) {
                throw new BusinessException("Paiement sandbox refuse : " + softpay.message());
            }
            checkoutUrl = softpay.url();
        } else if (dto.provider() == PaymentProvider.WAVE_SENEGAL) {
            PayDunyaSoftpayResponse softpay = payDunyaClient.payWithWave(new WaveSenegalRequest(
                    dto.customerName(), dto.customerEmail(), dto.phoneNumber(), invoiceToken
            ));
            if (!softpay.isSuccess()) {
                throw new BusinessException("Paiement Wave refuse : " + softpay.message());
            }
            checkoutUrl = softpay.url();
        } else {
            PayDunyaSoftpayResponse softpay = payDunyaClient.payWithOrangeMoney(new OrangeMoneySenegalRequest(
                    dto.customerName(), dto.customerEmail(), dto.phoneNumber(), invoiceToken
            ));
            if (!softpay.isSuccess()) {
                throw new BusinessException("Paiement Orange Money refuse : " + softpay.message());
            }
            checkoutUrl = softpay.url();
            if (softpay.other_url() != null) {
                omUrl = softpay.other_url().get("om_url");
                maxitUrl = softpay.other_url().get("maxit_url");
            }
        }

        PaymentTransaction transaction = PaymentTransaction.builder()
                .tenantId(tenantId)
                .membreId(dto.membreId())
                .montant(dto.montant())
                .mois(dto.mois())
                .phoneNumber(dto.phoneNumber())
                .provider(dto.provider())
                .status(PaymentStatus.PENDING)
                .invoiceToken(invoiceToken)
                .checkoutUrl(checkoutUrl)
                .build();
        transaction = transactionRepository.save(transaction);

        return new PaymentInitiateResponseDTO(
                transaction.getId(), invoiceToken, checkoutUrl, omUrl, maxitUrl, transaction.getStatus().name()
        );
    }

    @Override
    @Transactional
    public void handleWebhook(Map<String, String> callbackParams) {

        String receivedHash = callbackParams.get("data[hash]");
        if (receivedHash == null || !receivedHash.equalsIgnoreCase(sha512(payDunyaProperties.getMasterKey()))) {
            throw new BusinessException("Signature IPN PayDunya invalide");
        }

        String invoiceToken = callbackParams.get("data[invoice][token]");
        if (invoiceToken == null) {
            throw new BusinessException("Jeton de facture manquant dans l'IPN");
        }

        PaymentTransaction transaction = transactionRepository.findByInvoiceToken(invoiceToken)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction introuvable pour ce jeton"));

        if (transaction.getStatus() == PaymentStatus.COMPLETED) {
            return;
        }

        applyConfirmedStatus(transaction);
    }

    @Override
    @Transactional
    public PaymentStatusResponseDTO getStatus(String invoiceToken) {
        PaymentTransaction transaction = transactionRepository.findByInvoiceToken(invoiceToken)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction introuvable pour ce jeton"));

        if (transaction.getStatus() == PaymentStatus.PENDING) {
            applyConfirmedStatus(transaction);
        }

        return new PaymentStatusResponseDTO(
                transaction.getId(), transaction.getInvoiceToken(), transaction.getStatus().name(), transaction.getVersementId()
        );
    }

    /**
     * Ne jamais crediter un versement sur la seule foi de l'IPN (hash statique,
     * rejouable si la cle fuite) : on reconfirme toujours en serveur-a-serveur
     * via nos propres cles avant de changer le statut local.
     */
    private void applyConfirmedStatus(PaymentTransaction transaction) {
        PayDunyaConfirmResponse confirm = payDunyaClient.confirm(transaction.getInvoiceToken());
        String status = confirm.status();

        if ("completed".equalsIgnoreCase(status)) {
            if (transaction.getStatus() != PaymentStatus.COMPLETED) {
                TenantContext.set(transaction.getTenantId());
                try {
                    Versement versement = Versement.builder()
                            .membreId(transaction.getMembreId())
                            .montant(transaction.getMontant())
                            .mois(transaction.getMois())
                            .annee(Year.now().getValue())
                            .build();
                    versement = versementRepository.save(versement);
                    transaction.setVersementId(versement.getVersementId());
                } finally {
                    TenantContext.clear();
                }
                transaction.setStatus(PaymentStatus.COMPLETED);
                transactionRepository.save(transaction);
                if (!payDunyaProperties.isSandbox()) {
                    disburseToTenant(transaction);
                }
            }
        } else if ("cancelled".equalsIgnoreCase(status)) {
            transaction.setStatus(PaymentStatus.CANCELLED);
            transactionRepository.save(transaction);
        } else if ("failed".equalsIgnoreCase(status)) {
            transaction.setStatus(PaymentStatus.FAILED);
            transactionRepository.save(transaction);
        }
    }

    /**
     * Reverse le montant integral du versement sur le numero Wave/Orange Money
     * du tenant via l'API PUSH PayDunya (pas de commission retenue). Best-effort :
     * le versement est deja confirme et credite cote tenant quoi qu'il arrive ici,
     * donc un echec de reversement est logue mais ne fait pas echouer la
     * confirmation (sinon on la rejouerait indefiniment sur chaque appel de
     * statut/webhook).
     */
    private void disburseToTenant(PaymentTransaction transaction) {
        try {
            TenantServiceClient.TenantPayoutInfo payout = tenantServiceClient.getTenant(transaction.getTenantId());
            if (payout == null || payout.payoutPhoneNumber() == null || payout.payoutOperator() == null) {
                log.warn("Tenant {} sans numero de reversement configure, versement {} non reverse",
                        transaction.getTenantId(), transaction.getId());
                return;
            }

            int amount = (int) Math.round(transaction.getMontant());
            String withdrawMode = payout.payoutOperator() == PaymentProvider.WAVE_SENEGAL
                    ? "wave-senegal" : "orange-money-senegal";

            PayDunyaDisburseInvoiceResponse invoice = payDunyaDisburseClient.getInvoice(new PayDunyaDisburseInvoiceRequest(
                    payout.payoutPhoneNumber(), amount, withdrawMode, payDunyaProperties.getDisburseCallbackUrl()
            ));
            if (!invoice.isSuccess()) {
                log.error("Echec creation de la facture de reversement pour le versement {} (code {})",
                        transaction.getId(), invoice.response_code());
                return;
            }

            PayDunyaDisburseSubmitResponse submit = payDunyaDisburseClient.submitInvoice(new PayDunyaDisburseSubmitRequest(
                    invoice.disburse_token(), transaction.getInvoiceToken()
            ));
            if (!submit.isSuccess()) {
                log.error("Echec du reversement pour le versement {} : {}", transaction.getId(), submit.response_text());
            }
        } catch (Exception e) {
            log.error("Erreur lors du reversement au tenant {} pour le versement {}",
                    transaction.getTenantId(), transaction.getId(), e);
        }
    }

    private static String sha512(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 indisponible", e);
        }
    }
}
