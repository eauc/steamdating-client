Feature: Rounds Summary

  Background:
    Given some Rounds have been defined
    Given I open Rounds/Summary page

  Scenario:
    Then I see the full rounds summary
    When I invert the Summary sort order
    Then I see the full rounds summary in reverse order

  Scenario Outline: Filter Games
    When I filter the Summary with "<filter>"
    Then I see the matching Results

    Examples:
    | filter |
    | to     |
    | te     |

  Scenario:
    When I go to Round #2
    Then I see the Round #2 games
