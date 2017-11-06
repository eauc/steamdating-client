Feature: Create online tournament

  @online
  Scenario: Create online tournament
    Given I am logged in
    And some Rounds have been defined
    And I open Online page
    When I upload current tournament:
    | name         |       date |
    | MyTournament | 09/25/2017 |
    Then I can see the online tournament in the list:
    | name         |       date |
    | MyTournament | 2017-09-25 |
    When I open Data page
    And I start a new tournament
    And I open Online page
    And I download the online tournament "MyTournament"
    And I open Rounds/Summary page
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 3 | tyty   | 1/2 - Malekus1              | 4. Phantom |           | 2. tutu    | Malekus1   |
