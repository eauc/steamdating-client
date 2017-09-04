Feature: Players Filter & Sort

  Scenario Outline: Filter Players list
    Given more Players have been defined
    And I open Players/List page
    When I filter the Players list with "<filter>"
    Then I see Players/List page with the matching columns first

    Examples:
    | filter |
    | toto   |
    | lyon   |
    | kha    |
    | abs    |

  Scenario Outline: Sort Players list
    Given more Players have been defined
    And I open Players/List page
    When I sort the Players list by "<by>"
    Then I see Players/List page sorted by "<by>"
    When I invert the sort order
    Then I see Players/List page sorted by "<by>" in reverse order

    Examples:
    | by      |
    | Faction |
    | Origin  |
