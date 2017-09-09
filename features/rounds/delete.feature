Feature: Delete round

  Background:
    Given some Rounds have been defined

  Scenario: Delete a round
    Given I open Rounds/1 page
    When I delete current Round
    Then I see Rounds/1 page with games:
    | p1ap | p1cp | p1list     | player1 | table | player2 | p2list   | p2cp | p2ap |
    |  105 |    4 | Helynna1   | teuteu  |     1 | titi    | Feora1   |    3 |   10 |
    |   56 |    4 | Cyphon1    | tutu    |     2 | tyty    | Malekus1 |    0 |  103 |
    |   45 |    5 | Absylonia1 | toto    |     3 | toutou  | Lylyth2  |    0 |   75 |
    |    0 |    0 |            | Phantom |     4 | tete    | Koslov1  |    0 |    0 |

  Scenario: Delete last round
    Given I open Rounds/2 page
    When I delete current Round
    Then I see Rounds/1 page with games:
    | p1ap | p1cp | p1list   | player1 | table | player2 | p2list    | p2cp | p2ap |
    |   52 |    5 | Butcher2 | tete    |     1 | teuteu  | Vyros1    |    3 |   21 |
    |   32 |    3 | Amon1    | titi    |     2 | toto    | Bethayne1 |    5 |   75 |
    |   46 |    0 | Lylyth2  | toutou  |     3 | tutu    | Bartolo1  |    4 |   30 |
    |    0 |    0 |          | tyty    |     4 | Phantom |           |    0 |    0 |

  Scenario: Delete last remaining round
    Given I open Rounds/1 page
    When I delete current Round
    When I delete current Round
    Then I see the Rounds/Summary page with rounds:
    | tete   | / 2 |
    | teuteu | / 2 |
    | titi   | / 2 |
    | toto   | / 2 |
    | toutou | / 2 |
    | tutu   | / 2 |
    | tyty   | / 2 |
