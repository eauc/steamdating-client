(ns steamdating.components.player.list
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]
            [steamdating.components.filter.input :refer [filter-input]]))


(defn row
  [{:keys [columns on-click player]}]
  [:tr {:on-click #(on-click player)}
   (for [c columns]
     (condp = c
       :faction [:td {:key c}
                 [faction-icon player]
                 [:span " " (:faction player)]]
       :lists [:td {:key c} (s/join ", " (sort (:lists player)))]
       [:td {:key c} (c player)]))])


(defn headers
  [columns]
  [:thead
   [:tr
    (for [c columns]
      [:th {:key c}
       (s/capitalize (name c))])]])


(defn render-list
  [players {:keys [on-player-click]}]
  (let [columns (vec (:columns players))]
    [:table.sd-PlayersList-list
     [headers columns]
     [:tbody
      (for [{:keys [name] :as player} (:list players)]
        [row {:columns columns
              :key name
              :on-click on-player-click
              :player player}])]]))


(defn players-list
  []
  (let [players (re-frame/subscribe [:steamdating.players/list])
        player-edit #(re-frame/dispatch [:steamdating.players/start-edit %])]
    (fn list-component
      []
      [:div.sd-PlayersList
       [filter-input {:name :player}]
       [render-list @players
        {:on-player-click player-edit}]])))
