Feature: Import

  Scenario: T3 CSV
    Given I open File page
    When I import T3 CSV players file "t3_list.csv"
    And I open Players/List page
    Then I see Players/List page with players:
    | Feeder      | Saint Etienne          | Cygnar       |
    | Glanzer     | St Priest en Jarez     | Trollbloods  |
    | GRUXXKi     | Villars Saint Georges  | Convergence  |
    | Ju-         | Fréjus                 | Retribution  |
    | kedorev     | Saint Etienne          | Cryx         |
    | legaga      | Saint Etienne          | Skorne       |
    | seii        | Saint Etienne          | Legion       |
    | Tsuki       | Saint Etienne          | Circle       |
    | Umibozu     | Firminy                | Mercenaries  |
    | Urizen      | Montbrison             | Protectorate |
    | zeeromancer | Béziers, le désert ... | Khador       |
