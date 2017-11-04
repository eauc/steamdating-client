Feature: Ranking Filter & Sort

  Scenario: Filter Ranking 1
    Given more Rounds have been defined
    And I open Ranking page
    When I filter the Ranking with "te"
    Then I see the Ranking:
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |

  Scenario: Filter Ranking 2
    Given more Rounds have been defined
    And I open Ranking page
    When I filter the Ranking with "to"
    Then I see the Ranking:
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |

  Scenario: Sort Ranking 1
    Given more Rounds have been defined
    And I open Ranking page
    When I sort the Ranking by "Name"
    Then I see the Ranking:
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Click to drop |
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    When I invert the Ranking sort order
    Then I see the Ranking:
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Click to drop |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |

  Scenario: Sort Ranking 2
    Given more Rounds have been defined
    And I open Ranking page
    When I sort the Ranking by "SOS"
    Then I see the Ranking:
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |
    When I invert the Ranking sort order
    Then I see the Ranking:
    | 3 | tete   | Khador       | 2 | 7 | 38 | 616 | 1 | Click to drop |
    | 2 | toto   | Legion       | 2 | 7 | 41 | 872 | 2 | Click to drop |
    | 6 | titi   | Protectorate | 1 | 6 | 12 | 332 | 1 | Click to drop |
    | 5 | tutu   | Mercenaries  | 1 | 6 | 26 | 699 | 2 | Click to drop |
    | 4 | teuteu | Retribution  | 2 | 6 | 25 | 793 | 1 | Click to drop |
    | 1 | tyty   | Protectorate | 4 | 5 | 37 | 250 | 2 | Click to drop |
    | 7 | toutou | Legion       | 0 | 4 | 19 | 639 | 3 | Click to drop |
