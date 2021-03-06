Feature: Follow online tournament

  @online
  Scenario: Follow online tournament
    Given I am logged in
    And some Rounds have been defined
    And I open Online page
    When I upload current tournament:
    | name         |       date |
    | MyTournament | 09/25/2017 |
    And I follow current tournament
    Then I see the Follow page Rankings:
    | 1 | tete   | Khador       | 2 | 1 |  5 |  52 | 0 | - |
    | 2 | toto   | Legion       | 2 | 0 | 10 | 120 | 1 | - |
    | 3 | tyty   | Protectorate | 2 | 0 |  0 | 103 | 1 | - |
    | 4 | teuteu | Retribution  | 1 | 2 |  7 | 126 | 1 | - |
    | 5 | titi   | Protectorate | 0 | 3 |  6 |  42 | 0 | - |
    | 6 | tutu   | Mercenaries  | 0 | 2 |  8 |  86 | 0 | - |
    | 7 | toutou | Legion       | 0 | 2 |  0 | 121 | 1 | - |
    And I see the Follow page Round #2:
    | p1ap | p1cp | player1 | p1list     | table | player2 | p2list   | p2cp | p2ap |
    |  105 |    4 | teuteu  | Helynna1   |     1 | titi    | Feora1   |    3 |   10 |
    |   56 |    4 | tutu    | Cyphon1    |     2 | tyty    | Malekus1 |    0 |  103 |
    |   45 |    5 | toto    | Absylonia1 |     3 | toutou  | Lylyth2  |    0 |   75 |
    |    0 |    0 | Phantom |            |     4 | tete    | Koslov1  |    0 |    0 |
    And I see the Follow page Round #1:
    | p1ap | p1cp | player1 | p1list   | table | player2 | p2list    | p2cp | p2ap |
    |   52 |    5 | tete    | Butcher2 |     1 | teuteu  | Vyros1    |    3 |   21 |
    |   32 |    3 | titi    | Amon1    |     2 | toto    | Bethayne1 |    5 |   75 |
    |   46 |    0 | toutou  | Lylyth2  |     3 | tutu    | Bartolo1  |    4 |   30 |
    |    0 |    0 | tyty    |          |     4 | Phantom |           |    0 |    0 |
    And I see the Follow page Rounds summary:
    | 1 | tete   | 2/2 - Butcher2, Koslov1     | 1. teuteu  | Butcher2  | 4. Phantom | Koslov1    |
    | 4 | teuteu | 2/2 - Helynna1, Vyros1      | 1. tete    | Vyros1    | 1. titi    | Helynna1   |
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    | 6 | tutu   | 2/2 - Bartolo1, Cyphon1     | 3. toutou  | Bartolo1  | 2. tyty    | Cyphon1    |
    | 3 | tyty   | 1/2 - Malekus1              | 4. Phantom |           | 2. tutu    | Malekus1   |
    And I see the Follow page Players list:
    | tete   | lyon    | Khador       | Butcher2, Koslov1     |
    | teuteu | paris   | Retribution  | Helynna1, Vyros1      |
    | titi   | dijon   | Protectorate | Amon1, Feora1         |
    | toto   | lyon    | Legion       | Absylonia1, Bethayne1 |
    | toutou | paris   | Legion       | Absylonia1, Lylyth2   |
    | tutu   | aubagne | Mercenaries  | Bartolo1, Cyphon1     |
    | tyty   | nantes  | Protectorate | Malekus1, Severius1   |

  @online
  Scenario: Filter player name
    Given I am logged in
    And some Rounds have been defined
    And I open Online page
    When I upload current tournament:
    | name         |       date |
    | MyTournament | 09/25/2017 |
    And I follow current tournament
    And I filter followed tournament with "toto"
    Then I see the Follow page Rankings:
    | 2 | toto   | Legion       | 2 | 0 | 10 | 120 | 1 | - |
    And I see the Follow page Round #2:
    | p1ap | p1cp | player1 | p1list     | table | player2 | p2list   | p2cp | p2ap |
    |   45 |    5 | toto    | Absylonia1 |     3 | toutou  | Lylyth2  |    0 |   75 |
    And I see the Follow page Round #1:
    | p1ap | p1cp | player1 | p1list   | table | player2 | p2list    | p2cp | p2ap |
    |   32 |    3 | titi    | Amon1    |     2 | toto    | Bethayne1 |    5 |   75 |
    And I see the Follow page Rounds summary:
    | 5 | titi   | 2/2 - Amon1, Feora1         | 2. toto    | Amon1     | 1. teuteu  | Feora1     |
    | 2 | toto   | 2/2 - Absylonia1, Bethayne1 | 2. titi    | Bethayne1 | 3. toutou  | Absylonia1 |
    | 7 | toutou | 1/2 - Lylyth2               | 3. tutu    | Lylyth2   | 3. toto    | Lylyth2    |
    And I see the Follow page Players list:
    | toto   | lyon    | Legion       | Absylonia1, Bethayne1 |
