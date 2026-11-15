package com.hasalp.ctoulel_finance_service.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hasalp.ctoulel_finance_service.model.PaymentProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

/**
 * Appelle tenant-service (via Eureka, lb://tenant-service) pour recuperer le
 * numero Wave/Orange Money sur lequel reverser la part du tenant apres un
 * versement.
 */
@Component
public class TenantServiceClient {

    private final RestClient restClient;

    public TenantServiceClient(RestClient.Builder loadBalancedRestClientBuilder) {
        this.restClient = loadBalancedRestClientBuilder.baseUrl("lb://tenant-service").build();
    }

    public TenantPayoutInfo getTenant(UUID tenantId) {
        return restClient.get()
                .uri("/api/tenants/{tenantId}", tenantId)
                .retrieve()
                .body(TenantPayoutInfo.class);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TenantPayoutInfo(String payoutPhoneNumber, PaymentProvider payoutOperator) {
    }
}
