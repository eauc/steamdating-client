(ns steamdating.components.round.round
  (:require [re-frame.core :as re-frame]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.sort.header :refer [sort-header]]
            [steamdating.services.games]))


(defn headers
  [{:keys [on-sort-by]} sort]
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
   [:th.sd-RoundHeader-score "AP"]])


(defn game-row
  [{:keys [on-click]} game]
  [:tr.sd-RoundGameRow
   {:on-click on-click}
   [:td.sd-RoundGameRow-score
    (get-in game [:player1 :score :army] 0)]
   [:td.sd-RoundGameRow-score
    (get-in game [:player1 :score :scenario] 0)]
   [:td.sd-RoundGameRow-score
    (when (get-in game [:player1 :score :assassination])
      [icon "check"])]
   [:td.sd-RoundGameRow-name
    {:class (case (get-in game [:player1 :score :tournament])
              0 "sd-RoundGameRow-loss"
              1 "sd-RoundGameRow-win"
              nil nil)}
    [:div (or (get-in game [:player1 :name]) "Phantom")]
    [:div.sd-RoundGameRow-list.sd-text-muted
     (get-in game [:player1 :list])]]
   [:td.sd-RoundGameRow-faction
    [faction-icon (get-in game [:player1 :faction])]]
   [:td.sd-RoundGameRow-table
    (:table game)]
   [:td.sd-RoundGameRow-faction
    [faction-icon (get-in game [:player2 :faction])]]
   [:td.sd-RoundGameRow-name
    {:class (case (get-in game [:player2 :score :tournament])
              0 "sd-RoundGameRow-loss"
              1 "sd-RoundGameRow-win"
              nil nil)}
    [:div (or (get-in game [:player2 :name]) "Phantom")]
    [:div.sd-RoundGameRow-list.sd-text-muted
     (get-in game [:player2 :list])]]
   [:td.sd-RoundGameRow-score
    (when (get-in game [:player2 :score :assassination])
      [icon "check"])]
   [:td.sd-RoundGameRow-score
    (get-in game [:player2 :score :scenario] 0)]
   [:td.sd-RoundGameRow-score
    (get-in game [:player2 :score :army] 0)]])


(defn round
  [state sort {:keys [n-round] :as props}]
  [:table.sd-Round-list
   [:thead
    [headers props sort]]
   [:tbody
    (for [[n game] (map vector (range) (:games state))]
      [game-row {:key n
                 :on-click #(re-frame/dispatch [:steamdating.games/start-edit n-round game])}
       game])]])


(defn round-component
  [n-round]
  (let [state @(re-frame/subscribe [:steamdating.rounds/round-view n-round])
        sort @(re-frame/subscribe [:steamdating.sorts/sort :round {:by :table}])
        on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :round %])]
    [:div.sd-Round
     ;; (pr-str n state)
     [:h4 (str "Round #" (+ n-round 1))]
     [filter-input {:name :round}]
     [round state sort {:on-sort-by on-sort-by
                        :n-round n-round}]]))
