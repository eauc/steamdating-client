Feature: Basic UI Tests

  Background:
    Given I open Home page

  Scenario: Test Toaster
    When I test the "Toaster Success"
    Then the Toaster appears with a test message

  Scenario: Test Alert
    When I test the "Alert"
    Then the Alert appears with a test message
    When I validate the "Alert"
    Then the "Alert" disappears
    And the Toaster appears with the "alert-ok" message

  Scenario: Test Confirm Validate
    When I test the "Confirm"
    Then the Confirm appears with a test message
    When I validate the "Confirm"
    Then the "Confirm" disappears
    And the Toaster appears with the "confirm-ok" message

  Scenario: Test Confirm Cancel
    When I test the "Confirm"
    Then the Confirm appears with a test message
    When I cancel the "Confirm"
    Then the "Confirm" disappears
    And the Toaster appears with the "confirm-cancel" message

  Scenario: Test Prompt Validate
    When I test the "Prompt"
    Then the Prompt appears with a test message and a test value
    When I change the Prompt value to "71"
    And I validate the "Prompt"
    Then the "Prompt" disappears
    And the Toaster appears with the "prompt-ok : 71" message

  Scenario: Test Prompt Cancel
    When I test the "Prompt"
    Then the Prompt appears with a test message and a test value
    When I cancel the "Prompt"
    Then the "Prompt" disappears
    And the Toaster appears with the "prompt-cancel" message
