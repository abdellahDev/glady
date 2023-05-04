package com.wedoogift.identityprovider.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info =
@Info(
        title = "Wedoogift",
        version = "1.0",
        description = "Wedoogift Documentation of API Application"
)
)
public class APIDocConfig {
}
