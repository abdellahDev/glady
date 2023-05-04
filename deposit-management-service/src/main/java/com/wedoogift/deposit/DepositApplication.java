package com.wedoogift.deposit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.wedoogift.deposit")
public class DepositApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepositApplication.class, args);
	}

}
