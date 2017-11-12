(ns steamdating.components.player.list
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.form.filter-input :refer [filter-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.generics.sort-header :refer [sort-header]]
            [steamdating.components.player.file-imports :refer [player-file-imports]]))


(defn player-row
  [{:keys [columns icons on-player-click player]}]
  [:tr {:key (:name player)
        :on-click #(on-player-click player)}
   (for [c columns]
     [:td {:key c
           :class (name c)}
      (let [value (get player c)]
        (case c
          :faction [faction-icon {:key :icon
                                  :icons icons
                                  :name value}]
          :lists (s/join ", " (sort value))
          value))])])


(defn players-table
  [{:keys [caption filter? on-filter-update on-player-click on-sort-by state] :or {filter? true} :as props}]
  (let [{:keys [columns filter list icons sort]} state
        columns [:name :origin :faction :lists]]
    [:table.sd-table
     (-> props
         (dissoc :caption :filter? :on-filter-update :on-player-click :on-sort-by :state))
     [:caption
      [:div.sd-table-caption
       [:div.sd-table-caption-label "Players"]
       (when filter?
         [filter-input {:on-filter-update on-filter-update
                        :filter filter}])]]
     [:thead
      [:tr
       (for [c columns]
         [sort-header {:key c
                       :col c
                       :on-sort-by on-sort-by
                       :state sort}])]]
     [:tbody
      (for [player list]
        [player-row {:key (:name player)
                     :columns columns
                     :icons icons
                     :on-player-click on-player-click
                     :player player}])]]))


(defn player-list-render
  [{:keys [caption filter? on-filter-update on-player-click on-sort-by state] :or {filter? true} :as props}]
  (let [{:keys [columns filter list icons sort]} state
        columns [:name :origin :faction :lists]]
    [:div.sd-player-list
     [:div.sd-player-list-scrollable
      [players-table props]]
     [players-table (assoc props :class "sd-player-list-overlay")]]))


(defn player-list
  []
  (let [state @(re-frame/subscribe [:sd.players/list {:filter :players}])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :players %])
        on-player-click #(re-frame/dispatch [:sd.players.edit/start-edit %])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :players % :name])]
    (if (:render-list? state)
      [player-list-render {:on-filter-update on-filter-update
                           :on-player-click on-player-click
                           :on-sort-by on-sort-by
                           :state state}]
      [player-file-imports])))


(defn player-list-follow
  []
  (let [state @(re-frame/subscribe [:sd.players/list {:filter :follow}])
        ;; on-player-click #(re-frame/dispatch [:sd.players.edit/start-edit %])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :players % :name])]
    [player-list-render { ;; :on-player-click on-player-click
                         :filter? false
                         :on-sort-by on-sort-by
                         :state state}]))
