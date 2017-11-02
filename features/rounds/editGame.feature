Feature: Edit game

  Scenario: Update game
    Given some Rounds have been defined
    And I open Rounds/2 page
    When I start to edit Game with player "tyty"
    Then I can edit the Game information:
    | p1ap | p1cp | p1ck  | p1wl | p1list  | player1 | table | player2 | p2list   | p2wl | p2ck | p2cp | p2ap |
    |   56 |    4 | false | L    | Cyphon1 | tutu    |     2 | tyty    | Malekus1 | W    | true |    0 |  103 |
    When I enter Game results:
    | p1ap | p1cp | p1ck | p1list    | player1 | table | player2 | p2list     | p2ck  | p2cp | p2ap |
    |  105 |    1 | true | Bethayne1 | toto    |     5 | toutou  | Absylonia1 | false |   12 |   10 |
    And I save the Game
    Then I see Rounds/2 page with games:
    | p1ap | p1cp | p1list     | player1 | table | player2 | p2list     | p2cp | p2ap |
    |  105 |    4 | Helynna1   | teuteu  |     1 | titi    | Feora1     |    3 |   10 |
    |   45 |    5 | Absylonia1 | toto    |     3 | toutou  | Lylyth2    |    0 |   75 |
    |    0 |    0 |            | Phantom |     4 | tete    | Koslov1    |    0 |    0 |
    |  105 |    1 | Bethayne1  | toto    |     5 | toutou  | Absylonia1 |   12 |   10 |
