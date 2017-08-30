(ns steamdating.models.round-test
	(:require [clojure.test :refer [is are testing]]
						[devcards.core :as dc :refer-macros [deftest]]
						[steamdating.models.round :as round]))


(deftest round-model-test
	(testing "update-factions"
		(is (= {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
						:games [{:table 1
										 :player1 {:name "tete" :faction "Khador"}
										 :player2 {:name "Teuteu" :faction "Retribution"}}
										{:table 2
										 :player1 {:name "titi" :faction "Protectorate"}
										 :player2 {:name "Toto" :faction "Legion"}}
										{:table 3
										 :player1 {:name "toutou" :faction "Legion"}
										 :player2 {:name "Tutu" :faction "Mercenaries"}}
										{:table 4
										 :player1 {:name "tyty" :faction "Protectorate"}
										 :player2 {:name nil :faction nil}}]}
					 (round/update-factions
						 {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
							:games [{:table 1
											 :player1 {:name "tete"}
											 :player2 {:name "Teuteu"}}
											{:table 2
											 :player1 {:name "titi"}
											 :player2 {:name "Toto"}}
											{:table 3
											 :player1 {:name "toutou"}
											 :player2 {:name "Tutu"}}
											{:table 4
											 :player1 {:name "tyty"}
											 :player2 {:name nil}}]}
						 {"tete" "Khador"
							"Teuteu" "Retribution"
							"titi" "Protectorate"
							"Toto" "Legion"
							"toutou" "Legion"
							"Tutu" "Mercenaries"
							"tyty" "Protectorate"}))
				"Add players factions to games"))


	(testing "update-players-options"
		(is (= {:players {"Toto" "Toto"
											"titi" "> titi"
											"Tutu" "> Tutu"
											"tete" "tete"
											"tyty" "tyty"
											"toutou" "toutou"
											"Teuteu" "> Teuteu"}
						:games [{:table 1
										 :player1 {:name "tete"}
										 :player2 {:name nil}}
										{:table 2
										 :player1 {:name nil}
										 :player2 {:name "Toto"}}
										{:table 3
										 :player1 {:name "toutou"}
										 :player2 {:name nil}}
										{:table 4
										 :player1 {:name "tyty"}
										 :player2 {:name nil}}]}
					 (round/update-players-options
						 {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
							:games [{:table 1
											 :player1 {:name "tete"}
											 :player2 {:name nil}}
											{:table 2
											 :player1 {:name nil}
											 :player2 {:name "Toto"}}
											{:table 3
											 :player1 {:name "toutou"}
											 :player2 {:name nil}}
											{:table 4
											 :player1 {:name "tyty"}
											 :player2 {:name nil}}]}))
				"Create options object for round edit's selects"))


	(testing "results-for-player"
		(is (= [[{:table 2,:opponent "titi",:score {:tournament 1,:assassination true,:scenario 5,:army 75,:custom 0},
							:game {:table 2,
										 :player1 {:name "titi",:list "Amon1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 32,:custom 0}},
										 :player2 {:name "toto",:list "Bethayne1",
															 :score {:tournament 1,:assassination true,:scenario 5,:army 75,:custom 0}}}}
						 {:table 3,:opponent "toutou",:score {:tournament 1,:assassination false,:scenario 5,:army 45,:custom 0},
							:game {:table 3,
										 :player1 {:name "toto",:list "Absylonia1",
															 :score {:tournament 1,:assassination false,:scenario 5,:army 45,:custom 0}},
										 :player2 {:name "toutou",:list "Lylyth2",
															 :score {:tournament 0,:assassination false,:scenario 0,:army 75,:custom 0}}}}]
						[{:table 2,:opponent "toto",:score {:tournament 0,:assassination false,:scenario 3,:army 32,:custom 0},
							:game {:table 2,
										 :player1 {:name "titi",:list "Amon1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 32,:custom 0}},
										 :player2 {:name "toto",:list "Bethayne1",
															 :score {:tournament 1,:assassination true,:scenario 5,:army 75,:custom 0}}}}
						 {:table 1,:opponent "teuteu",:score {:tournament 0,:assassination false,:scenario 3,:army 10,:custom 0},
							:game {:table 1,
										 :player1 {:name "teuteu",:list "Helynna1",
															 :score {:tournament 1,:assassination true,:scenario 4,:army 105,:custom 0}},
										 :player2 {:name "titi",:list "Feora1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 10,:custom 0}}}}]
						[{:table 3,:opponent "toutou",:score {:tournament 0,:assassination false,:scenario 4,:army 30,:custom 0},
							:game {:table 3,
										 :player1 {:name "toutou",:list "Lylyth2",
															 :score {:tournament 0,:assassination true,:scenario 0,:army 46,:custom 0}},
										 :player2 {:name "tutu",:list "Bartolo1",
															 :score {:tournament 0,:assassination false,:scenario 4,:army 30,:custom 0}}}}
						 {:table 2,:opponent "tyty",:score {:tournament 0,:assassination false,:scenario 4,:army 56,:custom 0},
							:game {:table 2,
										 :player1 {:name "tutu",:list "Cyphon1",
															 :score {:tournament 0,:assassination false,:scenario 4,:army 56,:custom 0}},
										 :player2 {:name "tyty",:list "Malekus1",
															 :score {:tournament 1,:assassination true,:scenario 0,:army 103,:custom 0}}}}]
						[{:table 1,:opponent "teuteu",:score {:tournament 1,:assassination false,:scenario 5,:army 52,:custom 0},
							:game {:table 1,
										 :player1 {:name "tete",:list "Butcher2",
															 :score {:tournament 1,:assassination false,:scenario 5,:army 52,:custom 0}},
										 :player2 {:name "teuteu",:list "Vyros1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 21,:custom 0}}}}
						 {:table 4,:opponent nil,:score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0},
							:game {:table 4,
										 :player1 {:name nil,:list nil,
															 :score {:tournament 0,:assassination false,:scenario 0,:army 0,:custom 0}},
										 :player2 {:name "tete",:list "Koslov1",
															 :score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0}}}}]
						[{:table 4,:opponent nil,:score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0},
							:game {:table 4,
										 :player1 {:name "tyty",:list nil,
															 :score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0}},
										 :player2 {:name nil,:list nil,
															 :score {:tournament 0,:assassination false,:scenario 0,:army 0,:custom 0}}}}
						 {:table 2,:opponent "tutu",:score {:tournament 1,:assassination true,:scenario 0,:army 103,:custom 0},
							:game {:table 2,
										 :player1 {:name "tutu",:list "Cyphon1",
															 :score {:tournament 0,:assassination false,:scenario 4,:army 56,:custom 0}},
										 :player2 {:name "tyty",:list "Malekus1",
															 :score {:tournament 1,:assassination true,:scenario 0,:army 103,:custom 0}}}}]
						[{:table 3,:opponent "tutu",:score {:tournament 0,:assassination true,:scenario 0,:army 46,:custom 0},
							:game {:table 3,
										 :player1 {:name "toutou",:list "Lylyth2",
															 :score {:tournament 0,:assassination true,:scenario 0,:army 46,:custom 0}},
										 :player2 {:name "tutu",:list "Bartolo1",
															 :score {:tournament 0,:assassination false,:scenario 4,:army 30,:custom 0}}}}
						 {:table 3,:opponent "toto",:score {:tournament 0,:assassination false,:scenario 0,:army 75,:custom 0},
							:game {:table 3,
										 :player1 {:name "toto",:list "Absylonia1",
															 :score {:tournament 1,:assassination false,:scenario 5,:army 45,:custom 0}},
										 :player2 {:name "toutou",:list "Lylyth2",
															 :score {:tournament 0,:assassination false,:scenario 0,:army 75,:custom 0}}}}]
						[{:table 1,:opponent "tete",:score {:tournament 0,:assassination false,:scenario 3,:army 21,:custom 0},
							:game {:table 1,
										 :player1 {:name "tete",:list "Butcher2",
															 :score {:tournament 1,:assassination false,:scenario 5,:army 52,:custom 0}},
										 :player2 {:name "teuteu",:list "Vyros1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 21,:custom 0}}}}
						 {:table 1,:opponent "titi",:score {:tournament 1,:assassination true,:scenario 4,:army 105,:custom 0},
							:game {:table 1,
										 :player1 {:name "teuteu",:list "Helynna1",
															 :score {:tournament 1,:assassination true,:scenario 4,:army 105,:custom 0}},
										 :player2 {:name "titi",:list "Feora1",
															 :score {:tournament 0,:assassination false,:scenario 3,:army 10,:custom 0}}}}]]
					 (mapv
						 #(round/results-for-player
								%
								[{:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
									:games [{:table 1,
													 :player1 {:name "tete", :list "Butcher2",
																		 :score {:tournament 1, :assassination false, :scenario 5, :army 52, :custom 0}},
													 :player2 {:name "teuteu", :list "Vyros1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 21, :custom 0}}}
													{:table 2,
													 :player1 {:name "titi", :list "Amon1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 32, :custom 0}},
													 :player2 {:name "toto", :list "Bethayne1",
																		 :score {:tournament 1, :assassination true, :scenario 5, :army 75, :custom 0}}}
													{:table 3,
													 :player1 {:name "toutou", :list "Lylyth2",
																		 :score {:tournament 0, :assassination true, :scenario 0, :army 46, :custom 0}},
													 :player2 {:name "tutu", :list "Bartolo1",
																		 :score {:tournament 0, :assassination false, :scenario 4, :army 30, :custom 0}}}
													{:table 4,
													 :player1 {:name "tyty", :list nil,
																		 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}},
													 :player2 {:name nil, :list nil,
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}}}]}
								 {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
									:games [{:table 4,
													 :player1 {:name nil, :list nil,
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}},
													 :player2 {:name "tete", :list "Koslov1",
																		 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}}}
													{:table 1,
													 :player1 {:name "teuteu", :list "Helynna1",
																		 :score {:tournament 1, :assassination true, :scenario 4, :army 105, :custom 0}},
													 :player2 {:name "titi", :list "Feora1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 10, :custom 0}}}
													{:table 3,
													 :player1 {:name "toto", :list "Absylonia1",
																		 :score {:tournament 1, :assassination false, :scenario 5, :army 45, :custom 0}},
													 :player2 {:name "toutou", :list "Lylyth2",
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 75, :custom 0}}}
													{:table 2,
													 :player1 {:name "tutu", :list "Cyphon1",
																		 :score {:tournament 0, :assassination false, :scenario 4, :army 56, :custom 0}},
													 :player2 {:name "tyty", :list "Malekus1",
																		 :score {:tournament 1, :assassination true, :scenario 0, :army 103, :custom 0}}}]}])
						 ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"]))
				"Extracts all results for a player name"))


	(testing "lists-for-player"
		(is (= [#{"Absylonia1" "Bethayne1"}
						#{"Feora1" "Amon1"}
						#{"Cyphon1" "Bartolo1"}
						#{"Butcher2" "Koslov1"}
						#{"Malekus1"}
						#{"Lylyth2"}
						#{"Vyros1" "Helynna1"}]
					 (mapv
						 #(round/lists-for-player
								%
								[{:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
									:games [{:table 1,
													 :player1 {:name "tete", :list "Butcher2",
																		 :score {:tournament 1, :assassination false, :scenario 5, :army 52, :custom 0}},
													 :player2 {:name "teuteu", :list "Vyros1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 21, :custom 0}}}
													{:table 2,
													 :player1 {:name "titi", :list "Amon1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 32, :custom 0}},
													 :player2 {:name "toto", :list "Bethayne1",
																		 :score {:tournament 1, :assassination true, :scenario 5, :army 75, :custom 0}}}
													{:table 3,
													 :player1 {:name "toutou", :list "Lylyth2",
																		 :score {:tournament 0, :assassination true, :scenario 0, :army 46, :custom 0}},
													 :player2 {:name "tutu", :list "Bartolo1",
																		 :score {:tournament 0, :assassination false, :scenario 4, :army 30, :custom 0}}}
													{:table 4,
													 :player1 {:name "tyty", :list nil,
																		 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}},
													 :player2 {:name nil, :list nil,
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}}}]}
								 {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
									:games [{:table 4,
													 :player1 {:name nil, :list nil,
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}},
													 :player2 {:name "tete", :list "Koslov1",
																		 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}}}
													{:table 1,
													 :player1 {:name "teuteu", :list "Helynna1",
																		 :score {:tournament 1, :assassination true, :scenario 4, :army 105, :custom 0}},
													 :player2 {:name "titi", :list "Feora1",
																		 :score {:tournament 0, :assassination false, :scenario 3, :army 10, :custom 0}}}
													{:table 3,
													 :player1 {:name "toto", :list "Absylonia1",
																		 :score {:tournament 1, :assassination false, :scenario 5, :army 45, :custom 0}},
													 :player2 {:name "toutou", :list "Lylyth2",
																		 :score {:tournament 0, :assassination false, :scenario 0, :army 75, :custom 0}}}
													{:table 2,
													 :player1 {:name "tutu", :list "Cyphon1",
																		 :score {:tournament 0, :assassination false, :scenario 4, :army 56, :custom 0}},
													 :player2 {:name "tyty", :list "Malekus1",
																		 :score {:tournament 1, :assassination true, :scenario 0, :army 103, :custom 0}}}]}])
						 ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"]))
				"Extracts the lists set played by player name")))