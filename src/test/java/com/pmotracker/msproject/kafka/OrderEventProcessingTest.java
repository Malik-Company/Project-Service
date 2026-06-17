// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka
// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka-test
// MISSING DEPENDENCY: org.testcontainers:kafka
// MISSING DEPENDENCY: org.testcontainers:junit-jupiter
package com.pmotracker.msproject.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class OrderEventProcessingTest {

    // MISSING DEPENDENCY: org.testcontainers:kafka:1.17.6 or later
    @Container
    private static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    // MISSING DEPENDENCY: org.springframework.kafka:spring-kafka
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Consumer<String, String> consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOPIC = "order-events";

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        Map<String, Object> props = KafkaTestUtils.consumerProps(kafka.getBootstrapServers(), "test-group", "false");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        consumer = new DefaultKafkaConsumerFactory<String, String>(props).createConsumer();
        consumer.subscribe(Collections.singleton(TOPIC));
    }

    @AfterEach
    void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    void verifyOrderPlacementEventProcessing() throws JsonProcessingException {
        // Arrange
        String orderId = UUID.randomUUID().toString();
        OrderEvent orderEvent = new OrderEvent(orderId, "PLACED");
        String payload = objectMapper.writeValueAsString(orderEvent);

        // Act
        kafkaTemplate.send(TOPIC, orderId, payload);

        // Assert
        ConsumerRecord<String, String> received = KafkaTestUtils.getSingleRecord(consumer, TOPIC, 10000L); // 10 seconds timeout
        
        assertThat(received).isNotNull();
        assertThat(received.key()).isEqualTo(orderId);

        OrderEvent receivedEvent = objectMapper.readValue(received.value(), OrderEvent.class);
        assertThat(receivedEvent.getOrderId()).isEqualTo(orderId);
        assertThat(receivedEvent.getStatus()).isEqualTo("PLACED");
    }

    // Helper class for the event payload
    private static class OrderEvent {
        private String orderId;
        private String status;

        // Default constructor for Jackson
        public OrderEvent() {}

        public OrderEvent(String orderId, String status) {
            this.orderId = orderId;
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
