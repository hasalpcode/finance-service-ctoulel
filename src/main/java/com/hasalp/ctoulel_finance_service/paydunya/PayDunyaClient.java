package com.hasalp.ctoulel_finance_service.paydunya;

import com.hasalp.ctoulel_finance_service.paydunya.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "paydunya", url = "${paydunya.base-url}", configuration = PayDunyaFeignConfig.class)
public interface PayDunyaClient {

    @PostMapping("/checkout-invoice/create")
    PayDunyaInvoiceResponse createInvoice(@RequestBody PayDunyaInvoiceRequest request);

    @PostMapping("/softpay/wave-senegal")
    PayDunyaSoftpayResponse payWithWave(@RequestBody WaveSenegalRequest request);

    @PostMapping("/softpay/new-orange-money-senegal")
    PayDunyaSoftpayResponse payWithOrangeMoney(@RequestBody OrangeMoneySenegalRequest request);

    @PostMapping("/softpay/checkout/make-payment")
    PayDunyaSoftpayResponse payWithSandboxTestAccount(@RequestBody PayDunyaSandboxSoftpayRequest request);

    @GetMapping("/checkout-invoice/confirm/{token}")
    PayDunyaConfirmResponse confirm(@PathVariable("token") String token);
}
