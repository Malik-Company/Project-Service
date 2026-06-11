
package com.pmotracker.msproject.test;

// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka-test
// MISSING DEPENDENCY: org.springframework.kafka:spring-kafka
// MISSING DEPENDENCY: org.apache.kafka:kafka-clients

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { KafkaOrderEventTest.TOPIC })
public class KafkaOrderEventTest {

    static final String TOPIC = "orders";

    // MISSING DEPENDENCY: spring-kafka is needed for KafkaTemplate.
    // The application context may not have this bean configured without the dependency.
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(Collections.singleton(TOPIC));
    }

    @AfterEach
    void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    public void testOrderPlacementEventIsPublishedAndConsumed() {
        // Simulates an order placement event
        String orderId = "order-123";
        String orderEventPayload = "{\"orderId\":\"" + orderId + "\",\"status\":\"PLACED\"}";

        // Publishes the event to Kafka
        kafkaTemplate.send(TOPIC, orderEventPayload);

        // Verifies the consumer receives it
        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10).toMillis());

        assertEquals(1, records.count());
        String receivedEvent = records.iterator().next().value();

        assertNotNull(receivedEvent, "Event should have been received by the consumer.");

        assertTrue(receivedEvent.contains("\"orderId\":\"order-123\""), "The received event should contain the correct orderId.");
        assertTrue(receivedEvent.contains("\"status\":\"PLACED\""), "The received event should contain the correct status.");
    }
}
