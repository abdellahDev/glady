package com.wedoogift.identityprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.wedoogift.identityprovider")
public class IdentityproviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityproviderApplication.class, args);
	}

}
