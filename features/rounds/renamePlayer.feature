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
    | tete   | Butcher2, Koslov1 / 2     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | teuteu | Vyros1, Helynna1 / 2      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | titi   | Amon1, Feora1 / 2         | 2. tonton  | Amon1     | 1. teuteu  | Feora1     |
    | tonton | Bethayne1, Absylonia1 / 2 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | toutou | Lylyth2 / 2               | 3. tutu    | Lylyth2   | 3. tonton  | Lylyth2    |
    | tutu   | Bartolo1, Cyphon1 / 2     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | tyty   | Malekus1 / 2              | 4. Phantom |           | 2. tutu    | Malekus1   |
    When I open Rounds/1 page
    Then I see Rounds/1 page with games:
    | p1ap | p1cp | p1list   | player1 | table | player2 | p2list    | p2cp | p2ap |
    |   52 |    5 | Butcher2 | tete    |     1 | teuteu  | Vyros1    |    3 |   21 |
    |   32 |    3 | Amon1    | titi    |     2 | tonton  | Bethayne1 |    5 |   75 |
    |   46 |    0 | Lylyth2  | toutou  |     3 | tutu    | Bartolo1  |    4 |   30 |
    |    0 |    0 |          | tyty    |     4 | Phantom |           |    0 |    0 |
    When I open Rounds/2 page
    Then I see Rounds/2 page with games:
    | p1ap | p1cp | p1list     | player1 | table | player2 | p2list   | p2cp | p2ap |
    |  105 |    4 | Helynna1   | teuteu  |     1 | titi    | Feora1   |    3 |   10 |
    |   56 |    4 | Cyphon1    | tutu    |     2 | tyty    | Malekus1 |    0 |  103 |
    |   45 |    5 | Absylonia1 | tonton  |     3 | toutou  | Lylyth2  |    0 |   75 |
    |    0 |    0 |            | Phantom |     4 | tete    | Koslov1  |    0 |    0 |
