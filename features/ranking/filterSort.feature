Feature: Ranking Filter & Sort

  Scenario Outline: Filter Ranking
    Given more Rounds have been defined
    And I open Ranking page
    When I filter the Ranking with "<filter>"
    Then I see the matching players in Ranking

    Examples:
    | filter |
    | te     |
    | to     |

  Scenario Outline: Sort Ranking
    Given more Rounds have been defined
    And I open Ranking page
    When I sort the Ranking by "<by>"
    Then I see the Ranking sorted by "<by>"
    When I invert the Ranking sort order
    Then I see the Ranking sorted by "<by>" in reverse order

    Examples:
    | by     |
    | Player |
    | SOS    |
