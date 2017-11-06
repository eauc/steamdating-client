(ns steamdating.components.ranking.list
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.generics.sort-header :refer [sort-header]]
            [steamdating.models.player :as player]
            [steamdating.models.ranking :as ranking]
            [steamdating.services.debug :as debug]
            [steamdating.models.ui :as ui]))


(def scores [[:tournament "TP"]
             [:sos "SOS"]
             [:scenario "CP"]
             [:army "AP"]
             [:assassination "CK"]])


(defn list-caption
  [{:keys [filter? on-filter-update state] :or {filter? true} :as props}]
  (let [{:keys [filter]} state]
    [:caption
     [:div.sd-table-caption
      [:div.sd-table-caption-label "Rankings"]
      (when filter?
        [form-input {:on-update on-filter-update
                     :placeholder "Filter"
                     :value filter}])]]))


(defn list-headers
  [{:keys [on-sort-by state] :as props}]
  (let [{:keys [sort]} state]
    [:thead
     [:tr
      [sort-header {:class "sd-ranking-list-rank"
                    :col [:rank]
                    :label "#"
                    :on-sort-by on-sort-by
                    :state sort}]
      [sort-header {:class "sd-ranking-list-name"
                    :col [:name]
                    :label "Name"
                    :on-sort-by on-sort-by
                    :state sort}]
      [sort-header {:class "sd-ranking-list-faction"
                    :col [:faction]
                    :label "Faction"
                    :on-sort-by on-sort-by
                    :state sort}]
      (for [[score label] scores]
        [sort-header {:key score
                      :class "sd-ranking-list-score"
                      :col [:score score]
                      :label label
                      :on-sort-by on-sort-by
                      :state sort}])
      [sort-header {:class "sd-ranking-list-drop-after"
                    :col [:droped-after]
                    :label "Droped after"
                    :on-sort-by on-sort-by
                    :state sort}]]]))


(defn drop-cell
  [{:keys [edit? on-player-drop droped-after] :or {edit? true} :as props}]
  [:td.sd-ranking-list-drop-after
   (if edit?
     (if (some? droped-after)
       [:button.sd-ranking-list-drop
        {:on-click on-player-drop}
        [:div
         (str "Round #" droped-after)]
        [:div.sd-ranking-list-drop-hint
         "(Click to un-drop)"]]
       [:button.sd-ranking-list-drop
        {:on-click on-player-drop}
        [:div
         "Click to drop"]
        [:div.sd-ranking-list-drop-hint
         "(Click to drop)"]])
     [:button.sd-ranking-list-drop
      [:div
       (if (some? droped-after)
         (str "Round #" droped-after)
         "-")]])])


(defn player-row
  [{:keys [class edit? on-player-click on-player-drop state] :or {edit? true} :as props}]
  (let [{:keys [icons player]} state
        {:keys [droped-after faction name rank score]} player]
    [:tr.sd-ranking-list-player
     (-> props
         (dissoc :edit? :on-player-click :on-player-drop :state)
         (assoc :class (ui/classes class (when (some? droped-after) "droped"))))
     [:td.sd-ranking-list-rank
      rank]
     [:td.sd-ranking-list-name
      {:on-click on-player-click
       :title (player/->title player)}
      name]
     [:td.sd-ranking-list-faction
      [faction-icon {:icons icons
                     :name faction}]]
     (for [[s] scores]
       [:td.sd-ranking-list-score
        {:key s}
        (get score s 0)])
     [drop-cell {:droped-after droped-after
                 :edit? edit?
                 :on-player-drop on-player-drop}]]))


(defn ranking-list-table
  [{:keys [edit? on-filter-update on-player-click on-player-drop on-sort-by state] :or {edit? true} :as props}]
  (let [{:keys [filter icons rankings sort]} state]
    [:table.sd-table
     (dissoc props :edit? :filter? :on-filter-update :on-player-click :on-player-drop :on-sort-by :state)
     [list-caption props]
     [list-headers props]
     [:tbody
      (for [{:keys [droped-after faction name rank score] :as player} rankings]
        [player-row {:key name
                     :edit? edit?
                     :on-player-click #(on-player-click player)
                     :on-player-drop #(on-player-drop player)
                     :state (assoc state :player player)}])]]))


(defn ranking-list-render
  [props]
  [:div.sd-ranking-list
   ;; [:p (with-out-str (cljs.pprint/pprint rankings))]
   [:div.sd-ranking-list-scrollable
    [ranking-list-table props]]
   [ranking-list-table (assoc props :class "sd-ranking-list-overlay")]])


(defn ranking-list
  []
  (let [state @(re-frame/subscribe [:sd.rankings/list {:filter :rankings}])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :rankings %])
        on-player-click #(re-frame/dispatch [:sd.players.edit/start-edit %])
        on-player-drop #(re-frame/dispatch [:sd.players/toggle-drop %])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :rankings % :name])]
    [ranking-list-render
     {:on-filter-update on-filter-update
      :on-player-click on-player-click
      :on-player-drop on-player-drop
      :on-sort-by on-sort-by
      :state state}]))


(defn ranking-list-follow
  []
  (let [state @(re-frame/subscribe [:sd.rankings/list {:filter :follow}])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :rankings % :name])]
    [ranking-list-render
     {:edit? false
      :filter? false
      :on-sort-by on-sort-by
      :state state}]))
