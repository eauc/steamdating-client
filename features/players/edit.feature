Feature: Players Edit

  Scenario: Edit a Player
    Given some Players have been defined
    And I open Players/List page
    When I start to edit player "toto"
    Then I can edit the player information with:
    """
    {
      "name": "toto",
      "origin": "lyon",
      "faction": "Legion",
      "lists": ["Absylonia1", "Bethayne1"]
    }
    """
    When I enter player information:
    """
    {
      "name": "tete",
      "origin": "paris",
      "faction": "Cryx",
      "lists": ["Agathia1", "Asphyxious2"]
    }
    """
    And I save the player
    Then I see Players/List page with player:
    | tete | paris | Cryx | Agathia1, Asphyxious2 |
