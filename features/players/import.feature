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

  Scenario: CC JSON
    Given I open File page
    When I import CC JSON players file "cc_list.json"
    And I open Players/List page
    Then I see Players/List page with players:
    | Aaron Jones        | Retribution  | Helynna1, Vyros2        |
    | Adam Tricola       | Circle       | Baldur1, Wurmwood1      |
    | Alex Clark         | Protectorate | Thyra1, Vindictus1      |
    | Andrew King        | Convergence  | Axis1, Syntherion1      |
    | Andy Shellenbarger | Khador       | Irusk2, Vladimir1       |
    | Anthony Williams   | Legion       | Absylonia2, Fyanna2     |
    | Austin Mills       | Trollbloods  | Gunnbjorn1, Ragnor1     |
    | Chris Gilligan     | Cygnar       | Haley3, Sloan1          |
    | Chris Parker       | Skorne       | Morghoul2, Naaresh1     |
    | Egan Metzger       | Mercenaries  | Gorten1, Ossrum1        |
    | James Laird        | Protectorate | Durst1, Kreoss1         |
    | Jason Shrum        | Cryx         | Coven1, Deneghra1       |
    | Matt La Croix      | Minions      | Calaban1, Sturm1&Drang1 |
