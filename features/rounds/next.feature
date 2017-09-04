Feature: Rounds Next

  Scenario: Valid Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    Then I can edit the Next Round information
    When I enter games:
    | player1 | table | player2 |
    | tete    |     1 | Teuteu  |
    | titi    |     2 | Toto    |
    | toutou  |     3 | Tutu    |
    | tyty    |     4 |         |
    And I create the Next Round
    Then I see Rounds/1 page with games:
    | player1 | table | player2 |
    | tete    |     1 | Teuteu  |
    | titi    |     2 | Toto    |
    | toutou  |     3 | Tutu    |
    | tyty    |     4 | Phantom |

  Scenario: Change Player Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    Given I enter games:
    | player1 | table | player2 |
    | tete    |     1 | Teuteu  |
    |         |     2 | Toto    |
    |         |     3 | Tutu    |
    When I select "player1" name "Teuteu" for game #2
    Then "player2" for game #1 is empty

  Scenario: Partial Pairing
    Given more Players have been defined
    Given I open Rounds/Next page
    Given I enter games:
    | player1 | table | player2 |
    | tete    |     1 | Teuteu  |
    |         |     2 | Toto    |
    |         |     3 | Tutu    |
    Then I cannot create the Next Round
    And I see an error with the unpaired players:
    | titi   |
    | toutou |
    | tyty   |

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
