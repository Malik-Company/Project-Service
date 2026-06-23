Feature: Recorded Flow

  Scenario: Recorded flow (Positive)
    When I click the "Cookware" link
    And I click the "Rowenta" link
    And I click the "Handheld (3)" label
    And I click the "Handheld (3)" field
    And I fill the "Handheld (3)" field with "true"
    And I click the "Track Order" span
    And I click the "submit" field
