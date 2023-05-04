package com.wedoogift.deposit.factory;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class DepositConsumerFactory {

    private static final String KAFKA_TOPIC= "deposit.operation";


    private DepositConsumerFactory(){}

    public static ConsumerRecord<String, String> consumeFromKafka(KafkaContainer kafkaContainer) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  org.apache.kafka.common.serialization.StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class.getName());

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  org.apache.kafka.common.serialization.StringSerializer.class.getName());



        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(KAFKA_TOPIC));

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            if (records.isEmpty()) {
                return null;
            }

            return records.iterator().next();
        }
    }


}
