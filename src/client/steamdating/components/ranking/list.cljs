(ns steamdating.components.ranking.list
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.sort.header :refer [sort-header]]
            [steamdating.services.rankings]))


(defn list-headers
  [{:keys [on-sort-by]} sort]
  [:tr
   [sort-header sort
    {:name [:rank]
     :label "#"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:name]
     :label "Player"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:faction]
     :label "Faction"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:score :tournament]
     :label "TP"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:score :sos]
     :label "SOS"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:score :scenario]
     :label "CP"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:score :army]
     :label "AP"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:score :assassination]
     :label "CK"
     :on-sort-by on-sort-by}]
   [sort-header sort
    {:name [:droped-after]
     :label "Drop"
     :on-sort-by on-sort-by}]])


(defn droped
  [_ droped-after]
  (if (some? droped-after)
    [:button.sd-RankingList-undrop
     {:type :button}
     [:div "Droped after round #" droped-after]
     [:div.sd-text-muted "Click to un-drop"]]
    [:button.sd-RankingList-drop
     {:type :button}
     "Drop now"]))


(defn list-player
  [{:keys [on-player-edit on-toggle-drop-player] :as props}
   {:keys [droped-after] :as player} bests]
  [:tr.sd-RankingList-player
   {:class (when (some? droped-after)
             "sd-RankingList-droped")
    :on-click #(on-player-edit player)}
   [:td
    (:rank player)]
   [:td
    (:name player)]
   [:td {:class (when (= (get-in bests [:faction (:faction player) :name])
                         (:name player))
                  "sd-RankingList-best")}
    [faction-icon (:faction player)]
    [:span.sd-RankingList-faction
     " " (:faction player)]]
   [:td
    (get-in player [:score :tournament] 0)]
   [:td {:class (when (= (get-in player [:score :sos])
                         (get-in bests [:sos :value]))
                  "sd-RankingList-best")}
    (get-in player [:score :sos] 0)]
   [:td {:class (when (= (get-in player [:score :scenario])
                         (get-in bests [:scenario :value]))
                  "sd-RankingList-best")}
    (get-in player [:score :scenario] 0)]
   [:td {:class (when (= (get-in player [:score :army])
                         (get-in bests [:army :value]))
                  "sd-RankingList-best")}
    (get-in player [:score :army] 0)]
   [:td {:class (when (= (get-in player [:score :assassination])
                         (get-in bests [:assassination :value]))
                  "sd-RankingList-best")}
    (get-in player [:score :assassination] 0)]
   [:td.sd-RankingList-drop-col
    {:on-click #(do
                  (-> % .stopPropagation)
                  (on-toggle-drop-player player))}
    [droped props droped-after]]])


(defn ranking-list
  [{:keys [on-player-edit on-toggle-drop-player] :as props}
   ranking bests sort]
  [:table.sd-RankingList
   [:thead
    [list-headers props sort]]
   [:tbody
    (doall
      (for [player ranking]
        [list-player {:key (:name player)
                      :on-player-edit on-player-edit
                      :on-toggle-drop-player on-toggle-drop-player}
         player bests]))]])

(defn ranking-list-component
  []
  (let [ranking @(re-frame/subscribe [:steamdating.rankings/list])
        bests @(re-frame/subscribe [:steamdating.rankings/bests])
        sort @(re-frame/subscribe [:steamdating.sorts/sort :ranking {:by [:rank]}])]
    [ranking-list {:on-player-edit #(re-frame/dispatch [:steamdating.players/start-edit %])
                   :on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :ranking %])
                   :on-toggle-drop-player #(re-frame/dispatch [:steamdating.players/toggle-drop %])}
     ranking bests sort]))
