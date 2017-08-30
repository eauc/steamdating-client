(ns steamdating.components.round.summary
	(:require [re-frame.core :as re-frame]
						[steamdating.components.filter.input :refer [filter-input]]
						[steamdating.components.generics.faction-icon :refer [faction-icon]]
						[steamdating.components.sort.header :refer [sort-header]]
						[steamdating.models.game :as game]
						[clojure.string :as s]
						[steamdating.models.player :as player]))


(defn rounds-headers
	[n-rounds]
	(for [n (range n-rounds)]
		[:th
		 {:key n
			:class "sd-RoundsSummary-nth-header"}
		 [:a {:href (str "#/rounds/nth/" n)}
			(str "Round #" (+ n 1))]]))


(defn player-result
  [n result]
  [:td.sd-RoundsSummary-game
   {:key n
    :title (game/game->title (:game result))
    :class (case (get-in result [:score :tournament])
             0 "sd-RoundsSummary-loss"
             1 "sd-RoundsSummary-win"
             nil nil)}
   (:table result)
   ". "
   (or (:opponent result) "Phantom")])


(defn lists-status
  [{:keys [played-lists lists]}]
  [:td.sd-RoundsSummary-lists
   {:class (if (= (count played-lists) (count lists))
             "sd-RoundsSummary-lists-ok"
             "sd-RoundsSummary-lists-ko")}
   (s/join ", " played-lists) " / " (count lists)])


(defn player-summary
	[{:keys [n-rounds on-player-click]}
   {:keys [name faction lists played-lists results] :as player}]
	[:tr.sd-RoundsSummary-results
	 {:key name}
	 [:td.sd-RoundsSummary-name
		{:on-click #(on-player-click name)
		 :title (player/player->title player)}
		[faction-icon faction]
		" " name]
	 [lists-status player]
	 (for [[n result] (map vector (range n-rounds) results)]
		 (player-result n result))])


(defn summary
	[state {:keys [n-rounds sort on-sort-by] :as props}]
	[:table.sd-RoundsSummary-list
	 [:thead
		[:tr
		 [sort-header sort
			{:label "Player"
			 :name :player
			 :on-sort-by on-sort-by}]
		 [:th "Lists"]
		 (rounds-headers n-rounds)]]
	 [:tbody
		(for [player state]
			(player-summary props player))]])


(defn summary-component
	[]
	(let [n-rounds @(re-frame/subscribe [:steamdating.rounds/n-rounds])
				state @(re-frame/subscribe [:steamdating.rounds/summary])
				sort @(re-frame/subscribe [:steamdating.sorts/sort :rounds-all {:by :player}])]
		[:div.sd-RoundsSummary
		 [:h4 "Rounds summary"]
		 [filter-input
			{:name :rounds-all}]
		 [summary state
			{:n-rounds n-rounds
			 :sort sort
			 :on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :rounds-all %])
			 :on-player-click #(re-frame/dispatch [:steamdating.players/start-edit-name %])}]
		 ;; (pr-str state)
		 ]))
