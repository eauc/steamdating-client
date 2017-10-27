Feature: Players Create

  Scenario: Create Valid Player
    Given I start a new tournament
    And I open Players/List page
    When I start to create player
    Then I can edit the player information
    When I enter player information:
    """
    {
      "name": "Toto",
      "origin": "Lyon",
      "faction": "Legion",
      "lists": ["Fyanna2", "Absylonia1"],
      "notes": "Notes sur le joueur"
    }
    """
    And I create the player
    Then I see Players/List page with player:
    | Toto | Lyon   | Legion  | Absylonia1, Fyanna2 |

  Scenario: Player name is unique
    Given some Players have been defined
    And I open Players/Create page
    When I enter player information:
    """
    {
      "name": "toto"
    }
    """
    Then I cannot create the player because its name already exists
