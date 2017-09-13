Feature: Rounds Summary

  Background:
    Given some Rounds have been defined
    Given I open Rounds/Summary page

  Scenario: Default summary, sorted by name
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | Amon1, Feora1 / 2         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 2 | toto   | Bethayne1, Absylonia1 / 2 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | Lylyth2 / 2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    | 6 | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 3 | tyty   | Malekus1 / 2              | 4. Phantom |           | 2. tutu    | Malekus1   |
    When I invert the Summary sort order
    Then I see the Rounds/Summary page with the same rounds in reverse order

  Scenario: Sort by rank
    When I sort the Summary by "#"
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 2 | toto   | Bethayne1, Absylonia1 / 2 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 3 | tyty   | Malekus1 / 2              | 4. Phantom |           | 2. tutu    | Malekus1   |
    | 4 | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | Amon1, Feora1 / 2         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 6 | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 7 | toutou | Lylyth2 / 2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
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
    | p1ap | p1cp | p1list     | player1 | table | player2 | p2list   | p2cp | p2ap |
    |  105 |    4 | Helynna1   | teuteu  |     1 | titi    | Feora1   |    3 |   10 |
    |   56 |    4 | Cyphon1    | tutu    |     2 | tyty    | Malekus1 |    0 |  103 |
    |   45 |    5 | Absylonia1 | toto    |     3 | toutou  | Lylyth2  |    0 |   75 |
    |    0 |    0 |            | Phantom |     4 | tete    | Koslov1  |    0 |    0 |
