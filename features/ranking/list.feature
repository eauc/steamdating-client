Feature: Ranking List

  Scenario: Ranking List
    Given more Rounds have been defined
    When I open Ranking page
    Then I see the Rankings page:
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |
    And I see the Bests in faction:
    | Protectorate |    1 | tyty    |
    | Legion       |    2 | toto    |
    | Khador       |    3 | tete    |
    | Retribution  |    4 | teuteu  |
    | Mercenaries  |    5 | tutu    |
    And I see the Bests scores:
    | SOS           |   7 | tete, toto |
    | Scenario      |  41 | toto       |
    | Army          | 872 | toto       |
    | Assassination |   3 | toutou     |
