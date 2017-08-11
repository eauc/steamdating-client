(ns steamdating.components.player.list
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]))


(defn row
  [{:keys [player]}]
  [:tr
   [:td (:name player)]
   [:td (:origin player)]
   [:td
    [faction-icon player]
    [:span (:faction player)]]
   [:td (s/join ", " (sort (:lists player)))]])


(defn render-list
  [players]
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
                 :player player}])]]])

(defn players-list
  []
  (let [players (re-frame/subscribe [:steamdating.players/list])]
    (fn list-component
      []
      [render-list @players])))
