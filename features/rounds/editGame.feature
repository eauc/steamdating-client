Feature: Edit game

  Scenario: Update game
    Given some Rounds have been defined
    And I open Rounds/2 page
    When I start to edit Game with player "tyty"
    Then I can edit the Game information:
    | p1ap | p1cp | p1ck  | p1wl | player1 | table | player2 | p2wl | p2ck | p2cp | p2ap |
    |   56 |    4 | false | L    | tutu    |     2 | tyty    | W    | true |    0 |  103 |
    When I enter Game results:
    | p1ap | p1cp | p1ck | player1 | table | player2 | p2ck  | p2cp | p2ap |
    |  105 |    1 | true | toto    |     5 | toutou  | false |   12 |   10 |
    And I save the Game
    Then I see Rounds/2 page with games:
    | p1ap | p1cp | player1 | table | player2 | p2cp | p2ap |
    |  105 |    4 | teuteu  |     1 | titi    |    3 |   10 |
    |   45 |    5 | toto    |     3 | toutou  |    0 |   75 |
    |    0 |    0 | Phantom |     4 | tete    |    0 |    0 |
    |  105 |    1 | toto    |     5 | toutou  |   12 |   10 |
