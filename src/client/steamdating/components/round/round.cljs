(ns steamdating.components.round.round
  (:require [re-frame.core :as re-frame]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.sort.header :refer [sort-header]]))


(defn render-round
  [state sort {:keys [on-sort-by]}]
  [:table.sd-Round-list
   [:thead
    [:tr.sd-RoundHeader
     [:th.sd-RoundHeader-score "AP"]
     [:th.sd-RoundHeader-score "CP"]
     [:th.sd-RoundHeader-score "CK"]
     [sort-header sort {:name :player1
                        :label "Player1"
                        :on-sort-by on-sort-by}]
     [:th.sd-RoundHeader-faction]
     [sort-header sort {:name :table
                        :label "Table"
                        :on-sort-by on-sort-by}]
     [:th.sd-RoundHeader-faction]
     [sort-header sort {:name :player2
                        :label "Player2"
                        :on-sort-by on-sort-by}]
     [:th.sd-RoundHeader-score "CK"]
     [:th.sd-RoundHeader-score "CP"]
     [:th.sd-RoundHeader-score "AP"]]]
   [:tbody
    (for [game (:games state)]
      [:tr.sd-RoundGameRow
       {:key (str (get-in game [:player1 :name])
                  (get-in game [:player2 :name]))}
       [:td.sd-RoundGameRow-score
        (get-in game [:player1 :score :army] 0)]
       [:td.sd-RoundGameRow-score
        (get-in game [:player1 :score :scenario] 0)]
       [:td.sd-RoundGameRow-score
        (when (get-in game [:player1 :score :assassination])
          [icon "check"])]
       [:td
        (or (get-in game [:player1 :name]) "Phantom")]
       [:td.sd-RoundGameRow-faction
        [faction-icon (get-in game [:player1 :faction])]]
       [:td.sd-RoundGameRow-table
        (:table game)]
       [:td.sd-RoundGameRow-faction
        [faction-icon (get-in game [:player2 :faction])]]
       [:td
        (or (get-in game [:player2 :name]) "Phantom")]
       [:td.sd-RoundGameRow-score
        (when (get-in game [:player2 :score :assassination])
          [icon "check"])]
       [:td.sd-RoundGameRow-score
        (get-in game [:player2 :score :scenario] 0)]
       [:td.sd-RoundGameRow-score
        (get-in game [:player2 :score :army] 0)]])]])


(defn round
  [n]
  (let [state @(re-frame/subscribe [:steamdating.rounds/round-view n])
        sort @(re-frame/subscribe [:steamdating.sorts/sort :round {:by :table}])
        on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :round %])]
    [:div.sd-Round
     ;; (pr-str n state)
     [filter-input {:name :round}]
     [render-round state sort {:on-sort-by on-sort-by}]]))
