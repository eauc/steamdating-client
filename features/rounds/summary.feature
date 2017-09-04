Feature: Rounds Summary

  Background:
    Given some Rounds have been defined
    Given I open Rounds/Summary page

  Scenario:
    Then I see the Rounds/Summary page with rounds:
    | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | 4. Phantom |
    | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | 1. titi    |
    | titi   | Amon1, Feora1 / 2         | 2. toto    | 1. teuteu  |
    | toto   | Bethayne1, Absylonia1 / 2 | 2. titi    | 3. toutou  |
    | toutou | Lylyth2 / 2               | 3. tutu    | 3. toto    |
    | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | 2. tyty    |
    | tyty   | Malekus1 / 2              | 4. Phantom | 2. tutu    |
    When I invert the Summary sort order
    Then I see the Rounds/Summary page with the same rounds in reverse order

  Scenario Outline: Filter Games
    When I filter the Summary with "<filter>"
    Then I see the Summary's matching results

    Examples:
    | filter |
    | to     |
    | te     |

  Scenario:
    When I go from Summary to Round #2
    Then I see Rounds/2 page with games:
    | p1ap | p1cp | player1 | table | player2 | p2cp | p2ap |
    |  105 |    4 | teuteu  |     1 | titi    |    3 |   10 |
    |   56 |    4 | tutu    |     2 | tyty    |    0 |  103 |
    |   45 |    5 | toto    |     3 | toutou  |    0 |   75 |
    |    0 |    0 | Phantom |     4 | tete    |    0 |    0 |
