
package com.pmotracker.msproject.kafka;

// MISSING DEPENDENCY: org.springframework.boot:spring-boot-starter-test (ensure scope is test)
// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka
// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka-test
// MISSING DEPENDENCY: org.testcontainers:testcontainers:1.17.6
// MISSING DEPENDENCY: org.testcontainers:kafka:1.17.6
// MISSING DEPENDENCY: org.testcontainers:junit-jupiter:1.17.6
// MISSING DEPENDENCY: org.json:json:20190722

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
public class OrderEventKafkaIntegrationTest {

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.1"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        // Ensure producer settings are robust for tests
        registry.add("spring.kafka.producer.key-serializer", () -> "org.apache.kafka.common.serialization.StringSerializer");
        registry.add("spring.kafka.producer.value-serializer", () -> "org.apache.kafka.common.serialization.StringSerializer");
        registry.add("spring.kafka.producer.properties.linger.ms", () -> "10");
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order-events";

    @Test
    void testOrderPlacementEventPublishedAndConsumed() throws Exception {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        String eventPayload = new JSONObject()
                .put("orderId", orderId)
                .put("status", "PLACED")
                .toString();

        CountDownLatch latch = new CountDownLatch(1);
        final String[] consumedMessage = new String[1];

        // Setup consumer
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-" + UUID.randomUUID());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, String> consumer = consumerFactory.createConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC));

        // Act: produce event
        kafkaTemplate.send(TOPIC, orderId, eventPayload);
        kafkaTemplate.flush();

        // Assert: consume event in a separate thread
        Thread consumerThread = new Thread(() -> {
            try {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                if (!records.isEmpty()) {
                    consumedMessage[0] = records.iterator().next().value();
                    latch.countDown();
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();


        assertTrue(latch.await(15, TimeUnit.SECONDS), "Kafka message was not consumed in time");
        consumerThread.join();

        // Verify payload
        JSONObject consumedJson = new JSONObject(consumedMessage[0]);
        assertEquals(orderId, consumedJson.getString("orderId"));
        assertEquals("PLACED", consumedJson.getString("status"));
    }
}
