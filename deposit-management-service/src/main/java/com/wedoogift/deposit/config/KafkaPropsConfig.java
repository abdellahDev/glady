package com.wedoogift.deposit.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "deposit.kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaPropsConfig {
    private String topic;

}
