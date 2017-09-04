Feature: Players Delete

  Scenario: Delete a Player
    Given some Players have been defined
    And I open Players/List page
    When I delete player "toto"
    Then I see Players/List page without player "toto"
