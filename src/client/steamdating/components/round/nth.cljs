(ns steamdating.components.round.nth
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.sort-header :refer [sort-header]]
            [steamdating.models.ui :as ui]
            [steamdating.services.debug :as debug]
            [steamdating.services.games]
            [steamdating.services.rounds]))


(defn ck-cell
  [{:keys [game player]}]
  [:td.sd-round-nth-score
   [:span {:style       {:opacity (if (get-in game [player :score :assassination]) 1 0)}}
    [icon {:name "check"}]]])


(defn player-cell
  [{:keys [game player]}]
  [:td.sd-round-nth-player
   {:class (let [tp (get-in game [player :score :tournament])]
             (ui/classes (when (= 0 tp) "loss")
                         (when (= 1 tp) "win")))}
   [:div.sd-round-nth-player-content
    [:div.sd-round-nth-player-name
     (or (get-in game [player :name]) "Phantom")]
    [:div.sd-round-nth-player-list
     (get-in game [player :list] "")]]])


(defn faction-cell
  [{:keys [factions game icons player]}]
  [:td.sd-round-nth-faction
   [faction-icon
    {:icons icons
     :name (get factions (get-in game [player :name]))}]])


(defn game-row
  [{:keys [factions game icons] :as props}]
  [:tr (dissoc props :factions :game :icons)
   [:td.sd-round-nth-score
    (get-in game [:player1 :score :army] 0)]
   [:td.sd-round-nth-score
    (get-in game [:player1 :score :scenario] 0)]
   [ck-cell {:game game :player :player1}]
   [player-cell {:game game :player :player1}]
   [faction-cell {:factions factions :game game :icons icons :player :player1}]
   [:td.sd-round-nth-table
    (get-in game [:table] "")]
   [faction-cell {:factions factions :game game :icons icons :player :player2}]
   [player-cell {:game game :player :player2}]
   [ck-cell {:game game :player :player2}]
   [:td.sd-round-nth-score
    (get-in game [:player2 :score :scenario])]
   [:td.sd-round-nth-score
    (get-in game [:player2 :score :army])]])


(defn round-nth-render
  [{:keys [filter? on-filter-update on-game-click on-sort-by state] :or {filter? true}}]
  (let [{:keys [filter icons factions n round sort]} state
        {:keys [games]} round]
    [:div.sd-round-nth
     ;; [:p (with-out-str (cljs.pprint/pprint state))]
     [:table.sd-table
      [:caption
       [:div.sd-table-caption
        [:div.sd-table-caption-label (str "Round #" (inc n))]
        (when filter?
          [form-input {:on-update on-filter-update
                       :placeholder "Filter"
                       :value filter}])]]
      [:thead
       [:tr
        [:th.sd-round-nth-score "AP"]
        [:th.sd-round-nth-score "CP"]
        [:th.sd-round-nth-score "CK"]
        [sort-header {:col :player1
                      :on-sort-by on-sort-by
                      :state sort}]
        [:th.sd-round-nth-faction]
        [sort-header {:col :table
                      :on-sort-by on-sort-by
                      :state sort}]
        [:th.sd-round-nth-faction]
        [sort-header {:col :player2
                      :on-sort-by on-sort-by
                      :state sort}]
        [:th.sd-round-nth-score "CK"]
        [:th.sd-round-nth-score "CP"]
        [:th.sd-round-nth-score "AP"]]]
      [:tbody
       (for [[n game] (map vector (range) games)]
         [game-row {:key n
                    :game game
                    :icons icons
                    :factions factions
                    :on-click #(on-game-click n game)}])]]]))


(defn round-nth
  [{:keys [n]}]
  (let [state @(re-frame/subscribe [:sd.rounds/nth {:n n :filter :round}])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :round %])
        on-game-click #(re-frame/dispatch [:sd.games.edit/start n %2])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :round % :table])]
    [round-nth-render
     {:on-filter-update on-filter-update
      :on-game-click on-game-click
      :on-sort-by on-sort-by
      :state state}]))


(defn round-nth-follow
  [{:keys [n]}]
  (let [state @(re-frame/subscribe [:sd.rounds/nth {:n n :filter :follow}])
        ;; on-game-click #(re-frame/dispatch [:sd.games.edit/start n %2])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :round % :table])]
    [round-nth-render
     { ;; :on-game-click on-game-click
      :filter? false
      :on-sort-by on-sort-by
      :state state}]))
