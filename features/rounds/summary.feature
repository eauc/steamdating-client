Feature: Rounds Summary

  Background:
    Given some Rounds have been defined
    Given I open Rounds/Summary page

  Scenario: Default summary, sorted by name
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 3 | tyty   | 1/2 - Malekus1              | 4. Phantom |           | 2. tutu    | Malekus1   |
    When I invert the Summary sort order
    Then I see the Rounds/Summary page with the same rounds in reverse order

  # Scenario: Sort by rank
  #   When I sort the Summary by "#"
  #   Then I see the Rounds/Summary page with rounds:
  #   | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
  #   | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
  #   | 3 | tyty   | 1/2 - Malekus1              | 4. Phantom |           | 2. tutu    | Malekus1   |
  #   | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
  #   | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
  #   | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
  #   | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
  #   When I invert the Summary sort order
  #   Then I see the Rounds/Summary page with the same rounds in reverse order

  Scenario: Filter Games 1
    When I filter the Summary with "teuteu"
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |

  Scenario: Filter Games 1
    When I filter the Summary with "toutou"
    Then I see the Rounds/Summary page with rounds:
    | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
