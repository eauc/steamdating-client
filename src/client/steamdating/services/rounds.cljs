(ns steamdating.services.rounds
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.models.player :as player]
            [steamdating.models.round :as round]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))

(trace-forms
  {:tracer (tracer :color "green")}

  (db/reg-event-fx
    :steamdating.rounds/start-next
    [(re-frame/path [:tournament :players])]
    (fn start-next
      [{players :db} _]
      {:dispatch-n [[:steamdating.forms/reset :round (round/->round players)]
                    [:steamdating.routes/navigate "/rounds/next"]]}))


  (db/reg-event-fx
    :steamdating.rounds/update-edit-player
    [(re-frame/path [:forms :round :edit])]
    (fn update-edit-player
      [{form :db} [field name]]
      {:db (round/pair-player form field name)}))


  (db/reg-event-fx
    :steamdating.rounds/create-next
    (fn start-next
      [{:keys [db]} _]
      (let [new-round (get-in db [:forms :round :edit])]
        {:db (update-in db [:tournament :rounds] conj new-round)
         :dispatch [:steamdating.routes/navigate
                    (str "/rounds/nth/" (count (get-in db [:tournament :rounds])))]})))


  (db/reg-event-fx
    :steamdating.rounds/rename-player
    [(re-frame/path [:tournament :rounds])]
    (fn rename-player
      [{rounds :db} [{{old-name :name} :base
                      {new-name :name} :edit}]]
      {:db (mapv #(round/rename-player % old-name new-name)
                 rounds)}))


  (db/reg-event-fx
    :steamdating.rounds/random-score
    (fn random-score
      [{:keys [db]} [n-round]]
      (let [lists (player/lists (get-in db [:tournament :players]))]
        {:db (update-in db [:tournament :rounds n-round]
                        round/random-score lists)})))


  (re-frame/reg-sub
    :steamdating.rounds/rounds
    (fn rounds-sub
      [db [_ n]]
      (get-in db [:tournament :rounds])))


  (re-frame/reg-sub
    :steamdating.rounds/players-opponents
    :<- [:steamdating.rounds/rounds]
    :<- [:steamdating.players/names]
    (fn players-opponents-sub
      [[rounds names] _]
      (into {} (map (fn [name]
                      [name (round/opponents-for-player name rounds)])
                    names))))


  (re-frame/reg-sub
    :steamdating.rounds/edit
    :<- [:steamdating.forms/validate :round round/validate]
    :<- [:steamdating.players/factions]
    :<- [:steamdating.rounds/players-opponents]
    (fn edit-sub
      [[form-state factions opponents] _]
      (-> form-state
          (round/validate-pairings {:factions factions :opponents opponents})
          (update :edit round/update-factions factions)
          (update :edit round/update-players-options))))


  (re-frame/reg-sub
    :steamdating.rounds/n-rounds
    :<- [:steamdating.rounds/rounds]
    (fn n-rounds-sub
      [rounds _]
      (count rounds)))


  (re-frame/reg-sub
    :steamdating.rounds/round
    (fn round-sub
      [db [_ n]]
      (get-in db [:tournament :rounds n])))


  (re-frame/reg-sub
    :steamdating.rounds/round-view
    (fn round-view-input
      [[_ n] _]
      [(re-frame/subscribe [:steamdating.rounds/round n])
       (re-frame/subscribe [:steamdating.filters/pattern :round])
       (re-frame/subscribe [:steamdating.sorts/sort :round {:by :table}])
       (re-frame/subscribe [:steamdating.players/factions])])
    (fn round-view-sub
      [[round pattern sort factions] _]
      (-> round
          (round/filter-with pattern)
          (round/sort-with sort)
          (round/update-factions factions))))


  (re-frame/reg-sub
    :steamdating.rounds/players-results
    :<- [:steamdating.rounds/rounds]
    :<- [:steamdating.players/players]
    :<- [:steamdating.filters/pattern :rounds-all]
    (fn players-results-sub
      [[rounds players pattern] _]
      (->> players
           (filter #(re-find pattern (:name %)))
           (mapv #(assoc %
                         :results (round/results-for-player (:name %) rounds)
                         :played-lists (round/lists-for-player (:name %) rounds))))))


  (re-frame/reg-sub
    :steamdating.rounds/summary
    :<- [:steamdating.rounds/players-results]
    :<- [:steamdating.sorts/sort :rounds-all {:by :player}]
    (fn summary-sub
      [[results {:keys [by reverse]}] _]
      (-> results
          (->> (sort-by (fn [{:keys [name]}] (.toLowerCase name))))
          (cond-> reverse clojure.core/reverse)))))
