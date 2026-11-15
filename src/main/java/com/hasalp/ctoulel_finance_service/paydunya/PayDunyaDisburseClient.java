package com.hasalp.ctoulel_finance_service.paydunya;

import com.hasalp.ctoulel_finance_service.paydunya.dto.PayDunyaDisburseInvoiceRequest;
import com.hasalp.ctoulel_finance_service.paydunya.dto.PayDunyaDisburseInvoiceResponse;
import com.hasalp.ctoulel_finance_service.paydunya.dto.PayDunyaDisburseSubmitRequest;
import com.hasalp.ctoulel_finance_service.paydunya.dto.PayDunyaDisburseSubmitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * API PUSH (deboursement) de PayDunya : base url separee de l'API de collecte
 * (checkout-invoice/softpay). Doit etre active sur le dashboard PayDunya.
 */
@FeignClient(name = "paydunya-disburse", url = "${paydunya.disburse-base-url}", configuration = PayDunyaFeignConfig.class)
public interface PayDunyaDisburseClient {

    @PostMapping("/get-invoice")
    PayDunyaDisburseInvoiceResponse getInvoice(@RequestBody PayDunyaDisburseInvoiceRequest request);

    @PostMapping("/submit-invoice")
    PayDunyaDisburseSubmitResponse submitInvoice(@RequestBody PayDunyaDisburseSubmitRequest request);
}
