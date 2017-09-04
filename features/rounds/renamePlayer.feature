Feature: Rename player in rounds

  Scenario: Rename player
    Given some Rounds have been defined
    And I open Players/List page
    When I start to edit player "toto"
    When I enter player information:
    """
    {
      "name": "tonton"
    }
    """
    And I save the player
    Then I see Players/List page with player:
    | tonton | lyon | Legion | Absylonia1, Bethayne1 |
    When I open Rounds/Summary page
    Then I see the Rounds/Summary page with rounds:
    | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | 4. Phantom |
    | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | 1. titi    |
    | titi   | Amon1, Feora1 / 2         | 2. tonton  | 1. teuteu  |
    | tonton | Bethayne1, Absylonia1 / 2 | 2. titi    | 3. toutou  |
    | toutou | Lylyth2 / 2               | 3. tutu    | 3. tonton  |
    | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | 2. tyty    |
    | tyty   | Malekus1 / 2              | 4. Phantom | 2. tutu    |
    When I open Rounds/1 page
    Then I see Rounds/1 page with games:
    | p1ap | p1cp | player1 | table | player2 | p2cp | p2ap |
    |   52 |    5 | tete    |     1 | teuteu  |    3 |   21 |
    |   32 |    3 | titi    |     2 | tonton  |    5 |   75 |
    |   46 |    0 | toutou  |     3 | tutu    |    4 |   30 |
    |    0 |    0 | tyty    |     4 | Phantom |    0 |    0 |
    When I open Rounds/2 page
    Then I see Rounds/2 page with games:
    | p1ap | p1cp | player1 | table | player2 | p2cp | p2ap |
    |  105 |    4 | teuteu  |     1 | titi    |    3 |   10 |
    |   56 |    4 | tutu    |     2 | tyty    |    0 |  103 |
    |   45 |    5 | tonton  |     3 | toutou  |    0 |   75 |
    |    0 |    0 | Phantom |     4 | tete    |    0 |    0 |
