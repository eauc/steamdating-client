(ns steamdating.components.round.nth-test
	(:require [devcards.core :as dc :refer-macros [defcard-rg]]
						[reagent.core :as reagent]
						[steamdating.components.round.nth :refer [round-nth-render]]
						[steamdating.models.filter :refer [->pattern]]
						[steamdating.models.round :as round]
						[steamdating.models.sort :as sort]
						[steamdating.services.debug :as debug]))


(defcard-rg round-nth-test
	"Round nth component"


	(fn [state]
		(let [{:keys [filter lists round sort]} @state
          sorted-round (-> round
                           (round/filter-with (->pattern filter))
                           (round/sort-with sort))
          on-filter-update #(swap! state assoc :filter %)
					on-game-click #(println "game-click" %1 %2)
					on-sort-by #(swap! state update :sort sort/toggle-by %)]
			[:div
			 [:button.sd-button
				{:type :button
				 :on-click #(swap! state update :round round/random-score lists)}
				"Random"]
       [round-nth-render
        {:on-filter-update on-filter-update
         :on-game-click on-game-click
         :on-sort-by on-sort-by
         :state (assoc @state :round sorted-round)}]]))


	(reagent/atom
		{:filter ""
		 :sort {:by :table
						:reverse false}
		 :n 1
     :round {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
						 :games
						 [{:table 1
							 :player1 {:name "tyty"
												 :score {:tournament 1, :assassination true, :scenario 20, :army 3}
												 :list "Malekus1"}
							 :player2 {:name "toto"
												 :score {:tournament 0, :assassination false, :scenario 16, :army 287}
												 :list "Bethayne1"}}
							{:table 2
							 :player1 {:name "tete"
												 :score {:tournament 0, :assassination true, :scenario 18, :army 217}
												 :list "Koslov1"}
							 :player2 {:name "titi"
												 :score {:tournament 0, :assassination false, :scenario 2, :army 85}
												 :list "Feora1"}}
							{:table 3
							 :player1 {:name "teuteu"
												 :score {:tournament 0, :assassination false, :scenario 11, :army 295}
												 :list "Vyros1"}
							 :player2 {:name "tutu"
												 :score {:tournament 0, :assassination true, :scenario 16, :army 254}
												 :list "Bartolo1"}}
							{:table 4,
							 :player1 {:name "toutou"
												 :score {:tournament 0, :assassination true, :scenario 9, :army 272}
												 :list "Absylonia1"}
							 :player2 {:name nil
												 :score {:tournament 1, :assassination false, :scenario 13, :army 46}
												 :list nil}}]}
     :factions {"tyty" "Protectorate"
                "toto" "Legion"
                "tete" "Khador"
                "titi" "Protectorate"
                "teuteu" "Retribution"
                "tutu" "Mercenaries"
                "toutou" "Legion"}
     :lists {"tyty" #{"Malekus1" "Thyra1"}
             "toto" #{"Bethayne1" "Saeryn1"}
             "tete" #{"Koslov1" "Vladimir1"}
             "titi" #{"Feora1" "Harbinger1"}
             "teuteu" #{"Thyron1" "Viros1"}
             "tutu" #{"Bartolo1" "Magnus2"}
             "toutou" #{"Absylonia1" "Thagrosh1"}}
     :icons {:Cygnar "cygnar.png",
             :Retribution "retribution.png",
             :Khador "khador.png",
             :Legion "legion.png",
             :Trollbloods "trollbloods.png",
             :Minions "minions.png",
             :Protectorate "protectorate.png",
             :Grymkin "grymkin.png",
             :Cryx "cryx.png",
             :Skorne "skorne.png",
             :Mercenaries "mercenaries.png",
             :Convergence "convergence.png",
             :Circle "circle.png"}})


	{:inspect-data true
	 :history true})
