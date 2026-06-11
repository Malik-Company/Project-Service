package com.pmotracker.msproject.tests;

// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka
// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka-test

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }, topics = {"order.placement.topic"})
@Import(OrderEventProcessingTest.KafkaTestConsumer.class)
public class OrderEventProcessingTest {

    @Autowired
    private KafkaTemplate<String, String> producer;

    @Autowired
    private KafkaTestConsumer consumer;

    @Test
    public void testOrderPlacementEventIsPublishedAndConsumed() throws Exception {
        String orderId = "order-123";
        String status = "PLACED";
        String payload = String.format("{"orderId":"%s","status":"%s"}", orderId, status);

        producer.send("order.placement.topic", payload);

        String receivedMessage = consumer.getPayload();

        assertNotNull(receivedMessage, "Did not receive message from Kafka topic within timeout");

        // Using existing ObjectMapperUtil to verify payload
        var jsonObject = com.pmotracker.msproject.infrastructure.util.ObjectMapperUtil.toJSONObject(receivedMessage);
        assertEquals(orderId, jsonObject.getString("orderId"));
        assertEquals(status, jsonObject.getString("status"));
    }

    @Component
    public static class KafkaTestConsumer {
        private final BlockingQueue<String> payloadQueue = new LinkedBlockingQueue<>();

        @KafkaListener(topics = "order.placement.topic", groupId = "test-group", autoStartup = "true")
        public void receive(String payload) {
            this.payloadQueue.offer(payload);
        }

        public String getPayload() throws InterruptedException {
            return payloadQueue.poll(10, TimeUnit.SECONDS);
        }
    }
}
