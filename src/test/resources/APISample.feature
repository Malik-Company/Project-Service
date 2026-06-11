@TC_UIF2A_Commerce_03
Feature: Order Event Processing via Kafka
  As a QA engineer
  I want to verify that order events published to Kafka
  Are consumed and processed correctly

  Scenario: Verify order placement event is published and consumed
    Given a customer "David Patterson" places an order with orderId "ORD12345"
    When the order event is published to Kafka topic "orders"
    Then the Kafka consumer should receive the event with orderId "ORD12345"
    And the event status should be "PLACED"
