(ns steamdating.components.round.round
  (:require [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]))


(defn render-round
  [state]
  [:table.sd-Round-list
   [:thead
    [:tr.sd-RoundHeader
     [:th.sd-RoundHeader-score "AP"]
     [:th.sd-RoundHeader-score "CP"]
     [:th.sd-RoundHeader-score "CK"]
     [:th "Player1"]
     [:th.sd-RoundHeader-faction]
     [:th.sd-RoundHeader-table "Table"]
     [:th.sd-RoundHeader-faction]
     [:th "Player2"]
     [:th.sd-RoundHeader-score "CK"]
     [:th.sd-RoundHeader-score "CP"]
     [:th.sd-RoundHeader-score "AP"]]]
   [:tbody
    (for [game (:games state)]
      [:tr.sd-RoundGameRow
       {:key (str (get-in game [:player1 :name])
                  (get-in game [:player2 :name]))}
       [:td.sd-RoundGameRow-score
        (or (get-in game [:player1 :ap]) 0)]
       [:td.sd-RoundGameRow-score
        (or (get-in game [:player1 :cp]) 0)]
       [:td.sd-RoundGameRow-score
        (get-in game [:player1 :ck])]
       [:td
        (or (get-in game [:player1 :name]) "Phantom")]
       [:td.sd-RoundGameRow-faction
        [faction-icon {:faction (get-in game [:player1 :faction])}]]
       [:td.sd-RoundGameRow-table
        (:table game)]
       [:td.sd-RoundGameRow-faction
        [faction-icon {:faction (get-in game [:player2 :faction])}]]
       [:td
        (or (get-in game [:player2 :name]) "Phantom")]
       [:td.sd-RoundGameRow-score
        (get-in game [:player2 :ck])]
       [:td.sd-RoundGameRow-score
        (or (get-in game [:player2 :cp]) 0)]
       [:td.sd-RoundGameRow-score
        (or (get-in game [:player2 :ap]) 0)]])]])


(defn round
  [n]
  (let [state @(re-frame/subscribe [:steamdating.rounds/round-view n])]
    [:div.sd-Round
     ;; (pr-str n state)
     [render-round state]]))
