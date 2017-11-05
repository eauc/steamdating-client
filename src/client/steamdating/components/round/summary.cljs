(ns steamdating.components.round.summary
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.generics.sort-header :refer [sort-header]]
            [steamdating.models.ui :as ui]
            [steamdating.models.game :as game]
            [steamdating.models.player :as player]
            [steamdating.services.debug :as debug]
            [steamdating.services.games]
            [steamdating.services.rounds]))


(defn summary-caption
  [{:keys [on-filter-update state]}]
  (let [{:keys [filter]} state]
    [:caption
     [:div.sd-table-caption
      [:div.sd-table-caption-label "Rounds Summary"]
      [form-input {:on-update on-filter-update
                   :placeholder "Filter"
                   :value filter}]]]))


(defn summary-headers
  [{:keys [on-sort-by state] :as props}]
  (let [{:keys [n-rounds sort]} state]
    [:thead
     [:tr
      [sort-header {:class "sd-round-summary-rank"
                    :col :rank
                    :label "#"
                    :on-sort-by on-sort-by
                    :state sort}]
      [sort-header {:class "sd-round-summary-player"
                    :col :name
                    :label "Player"
                    :on-sort-by on-sort-by
                    :state sort}]
      [:th.sd-round-summary-faction]
      [:th.sd-round-summary-lists
       "Lists"]
      (for [n (range n-rounds)]
        [:th.sd-round-summary-round {:key n}
         (str "Round #" (inc n))])]]))


(defn lists-cell
  [{:keys [state]}]
  (let [{:keys [lists player]} state
        plists (get lists (:name player))
        n-played (count plists)
        n-total (count (:lists player))]
    [:td.sd-round-summary-lists
     {:class (when (= n-total n-played) "ok")}
     (str n-played "/" n-total
          (when-not (empty? plists)
            (str " - " (s/join ", " (sort plists)))))]))


(defn result-cell
  [{:keys [droped-after n result] :as props}]
  (let [{:keys [game list opponent score table]} result
        {:keys [tournament]} score
        droped? (and (some? droped-after) (>= n droped-after))
        no-game? (nil? result)]
    [:td.sd-round-summary-round
     (-> props
         (dissoc :droped-after :n :result)
         (assoc :class (ui/classes (when (or no-game? droped?) "droped")
                                   (when (= 0 tournament) "loss")
                                   (when (= 1 tournament) "win"))
                :title (game/->title game)))
     (cond
       droped? [:div.sd-round-summary-opponent "Droped"]
       no-game? [:div.sd-round-summary-opponent "N/A"]
       :else (cljs.core/list
               [:div.sd-round-summary-opponent
                {:key :opp}
                (str table ". " (or opponent "Phantom"))]
               [:div.sd-round-summary-list
                {:key :list}
                list]))]))


(defn player-row
  [{:keys [on-game-click on-player-click state] :as props}]
  (let [{:keys [icons player]} state
        {:keys [droped-after name faction rank results]} player]
    [:tr (dissoc props :state :on-game-click :on-player-click)
     [:td.sd-round-summary-rank
      rank]
     [:td.sd-round-summary-player
      {:on-click #(on-player-click player)
       :title (player/->title player)}
      name]
     [:td.sd-round-summary-faction
      [faction-icon {:icons icons
                     :name faction}]]
     [lists-cell props]
     (for [[n result] (map vector (range) (:results player))]
       [result-cell {:key n
                     :droped-after droped-after
                     :n n
                     :on-click #(on-game-click n (:game result))
                     :result result}])]))


(defn round-summary-table
  [{:keys [on-game-click on-player-click state] :as props}]
  (let [{:keys [players]} state]
    [:table.sd-table
     (dissoc props :on-filter-update :on-game-click :on-player-click :on-sort-by :state)
     [summary-caption props]
     [summary-headers props]
     [:tbody
      (for [player players]
        [player-row {:key (:name player)
                     :on-game-click on-game-click
                     :on-player-click on-player-click
                     :state (assoc state :player player)}])]]))


(defn round-summary-render
  [props]
  [:div.sd-round-summary
   ;; [:p (with-out-str (cljs.pprint/pprint (map :name (:players state))))]
   [:div.sd-round-summary-scrollable
    [round-summary-table props]]
   [round-summary-table (assoc props :class "sd-round-summary-overlay")]])


(defn round-summary
  []
  (let [state @(re-frame/subscribe [:sd.rounds/summary {:filter :rounds-all}])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :rounds-all %])
        on-game-click #(re-frame/dispatch [:sd.games.edit/start %1 %2])
        on-player-click #(re-frame/dispatch [:sd.players.edit/start-edit %])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :rounds-all % :name])]
    [round-summary-render
     {:on-filter-update on-filter-update
      :on-game-click on-game-click
      :on-player-click on-player-click
      :on-sort-by on-sort-by
      :state state}]))
