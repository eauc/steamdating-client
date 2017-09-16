Feature: Ranking List

  Scenario: Ranking List
    Given more Rounds have been defined
    When I open Ranking page
    Then I see the Ranking:
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Drop now |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Drop now |
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Drop now |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Drop now |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Drop now |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Drop now |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Drop now |
    And I see the Bests in faction:
    | Faction      | Rank | Players |
    | Protectorate |    1 | tyty    |
    | Legion       |    2 | toto    |
    | Khador       |    3 | tete    |
    | Retribution  |    4 | teuteu  |
    | Mercenaries  |    5 | tutu    |
    And I see the Bests scores:
    |               | Score | Players    |
    | SOS           |     7 | toto, tete |
    | Scenario      |    41 | toto       |
    | Army          |   872 | toto       |
    | Assassination |     3 | toutou     |
