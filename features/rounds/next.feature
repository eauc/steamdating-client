Feature: Rounds Next

  Scenario: Start Next
    Given more Players have been defined
    Given I open Rounds/Next page
    Then I can edit the Next Round information

  Scenario: Valid Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    When I pair all available players
    And I create the Next Round
    Then I see the New Round's page

  Scenario: Change Player Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    Given some players are paired
    When I select a player who is already paired
    Then the player name is removed from its previous pairing

  Scenario: Partial Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    When I pair some players
    Then I cannot create the Next Round
    And I see an error with the unpaired players names

  Scenario: Already Played
    Given some Rounds have been defined
    Given I open Rounds/Next page
    When I enter games:
    | player1 | table | player2 |
    | tete    |     1 | teuteu  |
    | titi    |     2 | tutu    |
    | toutou  |     3 | toto    |
    | tyty    |     4 |         |
    Then I cannot create the Next Round
    And I see an error with the already played pairings:
    | tete   | teuteu |
    | toutou | toto   |
