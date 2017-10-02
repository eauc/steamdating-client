(ns steamdating.components.player.list
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.player.file-import :refer [file-import]]
            [steamdating.components.sort.header :refer [sort-header]]))


(defn row
  [{:keys [columns on-click player]}]
  [:tr {:on-click #(on-click player)}
   (for [c columns]
     (condp = c
       :faction [:td {:key c}
                 [faction-icon (:faction player)]
                 [:span " " (:faction player)]]
       :lists [:td {:key c} (s/join ", " (sort (:lists player)))]
       [:td {:key c} (c player)]))])


(defn headers
  [sort {:keys [columns on-sort-by]}]
  [:thead
   [:tr
    (for [c columns]
      ^{:key c} [sort-header sort
                 {:name c
                  :label (s/capitalize (name c))
                  :on-sort-by on-sort-by}])]])


(defn render-list
  [players sort {:keys [on-player-click on-sort-by]}]
  (let [columns (vec (:columns players))]
    [:table.sd-PlayersList-list
     [headers sort {:columns columns
                    :on-sort-by on-sort-by}]
     [:tbody
      (for [{:keys [name] :as player} (:list players)]
        [row {:columns columns
              :key name
              :on-click on-player-click
              :player player}])]]))


(defn players-list
  [{:keys [edit? filter]
    :or {edit? true
         filter :player}}]
  (let [players @(re-frame/subscribe [:steamdating.players/list filter])
        sort @(re-frame/subscribe [:steamdating.sorts/sort :player {:by :name}])
        on-player-edit #(when edit? (re-frame/dispatch [:steamdating.players/start-edit %]))
        on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :player %])]
    (if (empty? (:list players))
      [file-import]
      [render-list players sort
       {:on-player-click on-player-edit
        :on-sort-by on-sort-by}])))
