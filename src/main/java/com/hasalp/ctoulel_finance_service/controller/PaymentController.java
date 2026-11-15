package com.hasalp.ctoulel_finance_service.controller;

import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentStatusResponseDTO;
import com.hasalp.ctoulel_finance_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/versement/initiate")
    public PaymentInitiateResponseDTO initiate(@Valid @RequestBody PaymentInitiateRequestDTO dto) {
        return service.initiate(dto);
    }

    @GetMapping("/{invoiceToken}/status")
    public PaymentStatusResponseDTO status(@PathVariable String invoiceToken) {
        return service.getStatus(invoiceToken);
    }

    @PostMapping(value = "/webhook/paydunya", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void webhook(@RequestParam Map<String, String> callbackParams) {
        service.handleWebhook(callbackParams);
    }

    /**
     * PayDunya exige une callback_url joignable pour l'API PUSH (deboursement),
     * mais le resultat est deja verifie en synchrone via check-status juste
     * apres submit-invoice (cf PaymentServiceImpl). Ce point d'entree ne fait
     * qu'acquitter l'appel.
     */
    @PostMapping("/webhook/paydunya-disburse")
    public void disburseWebhook(@RequestBody(required = false) Map<String, Object> payload) {
    }
}
