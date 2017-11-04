Feature: Drop player

  Scenario: Drop a player after some rounds
    Given some Rounds have been defined
    When I open Ranking page
    And I drop player "toutou"
    Then I see the player "toutou" has droped after round #2
    When I open Rounds/Next page
    Then I can edit the Next Round information for players:
    | tete | teuteu | titi | toto | tutu | tyty |
    When I ask for a SR pairing suggestion
    Then player "toutou #7" is not in Next Round pairings
    And I can edit 3 pairings for Next Round
    When I enter games:
    | player1 | table | player2 |
    | tete    |     1 | tyty    |
    | tutu    |     2 | titi    |
    | toto    |     3 | teuteu  |
    And I create the Next Round
    Then I see Rounds/3 page with games:
    | player1 | table | player2 |
    | tete    |     1 | tyty    |
    | tutu    |     2 | titi    |
    | toto    |     3 | teuteu  |
    When I open Rounds/Summary page
    Then I see the Rounds/Summary page with rounds:
    | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    | 1. tyty   |
    | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   | 3. toto   |
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     | 2. tutu   |
    | 3 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 | 3. teuteu |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    | Droped    |
    | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    | 2. titi   |
    | 2 | tyty   | 1/2 - Malekus1              | 4. Phantom |           | 2. tutu    | Malekus1   | 1. tete   |
    When I open Ranking page
    And I un-drop player "toutou"
    Then I see the player "toutou" has not droped
    When I open Rounds/Next page
    Then I can edit the Next Round information for players:
    | tete | teuteu | titi | toto | toutou | tutu | tyty |
    When I ask for a SR pairing suggestion
    Then player "toutou #7" is in Next Round pairings
    And I can edit 4 pairings for Next Round
