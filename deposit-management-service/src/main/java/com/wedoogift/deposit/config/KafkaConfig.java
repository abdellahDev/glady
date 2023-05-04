package com.wedoogift.deposit.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

        @Bean
        public NewTopic depositPublishedTopic(final KafkaPropsConfig kafkaConfigProps) {
            return TopicBuilder.name(kafkaConfigProps.getTopic())
                               .partitions(10)
                               .replicas(1)
                               .build();    }
}
