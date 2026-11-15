package com.hasalp.ctoulel_finance_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CtoulelFinanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CtoulelFinanceServiceApplication.class, args);
	}

}
