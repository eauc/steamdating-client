(ns steamdating.components.player.list
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]))


(defn row
  [{:keys [on-click player]}]
  [:tr {:on-click #(on-click player)}
   [:td (:name player)]
   [:td (:origin player)]
   [:td
    [faction-icon player]
    [:span (:faction player)]]
   [:td (s/join ", " (sort (:lists player)))]])


(defn render-list
  [players {:keys [on-player-click]}]
  [:div.sd-PlayersList
       [:table.sd-PlayersList-list
        [:thead
         [:tr
          [:th "Name"]
          [:th "Origin"]
          [:th "Faction"]
          [:th "Lists"]]]
        [:tbody
         (for [{:keys [name] :as player} players]
           [row {:key name
                 :on-click on-player-click
                 :player player}])]]])

(defn players-list
  []
  (let [players (re-frame/subscribe [:steamdating.players/list])
        player-edit #(re-frame/dispatch [:steamdating.players/start-edit %])]
    (fn list-component
      []
      [render-list @players
       {:on-player-click player-edit}])))
