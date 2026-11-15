package com.hasalp.ctoulel_finance_service.paydunya;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PayDunyaProperties.class)
public class PayDunyaFeignConfig {

    @Bean
    public RequestInterceptor payDunyaAuthInterceptor(PayDunyaProperties props) {
        return requestTemplate -> requestTemplate
                .removeHeader("Content-Type")
                .header("Content-Type", "application/json")
                .removeHeader("PAYDUNYA-MASTER-KEY")
                .header("PAYDUNYA-MASTER-KEY", props.getMasterKey())
                .removeHeader("PAYDUNYA-PRIVATE-KEY")
                .header("PAYDUNYA-PRIVATE-KEY", props.getPrivateKey())
                .removeHeader("PAYDUNYA-TOKEN")
                .header("PAYDUNYA-TOKEN", props.getToken());
    }
}
