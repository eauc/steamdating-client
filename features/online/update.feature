Feature: Update online tournament

  @online
  Scenario: Update online tournament
    Given I am logged in
    And some Rounds have been defined
    And I open Online page
    When I upload current tournament:
    | name         |       date |
    | MyTournament | 09/25/2017 |
    When I open Players/List page
    And I start to edit player "toto"
    And I enter player information:
    """
    {
      "name": "tonton"
    }
    """
    And I save the player
    When I open Online page
    And I upload current tournament:
    | name                | date       |
    | MyUpdatedTournament | 09/26/2017 |
    Then I can see the online tournament in the list:
    | name                |       date |
    | MyUpdatedTournament | 2017-09-26 |
    When I open File page
    And I start a new tournament
    When I open Online page
    And I download the online tournament "MyUpdatedTournament"
    And I open Rounds/Summary page
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | Amon1, Feora1 / 2         | 2. tonton  | Amon1     | 1. teuteu  | Feora1     |
    | 2 | tonton | Bethayne1, Absylonia1 / 2 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | Lylyth2 / 2               | 3. tutu    | Lylyth2   | 3. tonton  | Lylyth2    |
    | 6 | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 3 | tyty   | Malekus1 / 2              | 4. Phantom |           | 2. tutu    | Malekus1   |