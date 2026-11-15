package com.hasalp.ctoulel_finance_service.service;

import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentInitiateResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.PaymentStatusResponseDTO;

import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponseDTO initiate(PaymentInitiateRequestDTO dto);

    void handleWebhook(Map<String, String> callbackParams);

    PaymentStatusResponseDTO getStatus(String invoiceToken);
}
