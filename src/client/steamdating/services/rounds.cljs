(ns steamdating.services.rounds
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [clojure.test.check.generators :as gen]
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
      {:dispatch-n [[:steamdating.forms/reset :round (round/->round (player/on-board players))]
                    [:steamdating.routes/navigate "/rounds/next"]]}))


  (db/reg-event-fx
    :steamdating.rounds/update-edit-player
    [(re-frame/path [:forms :round :edit])]
    (fn update-edit-player
      [{form :db} [field name]]
      {:db (round/pair-player form field name)}))


  (db/reg-event-fx
    :steamdating.rounds/edit-sr-pairing
    (fn edit-sr-pairing
      [{:keys [db]}]
      (let [players (gen/generate
                      (gen/shuffle
                        (player/on-board (get-in db [:tournament :players]))))
            rounds (get-in db [:tournament :rounds])]
        {:db (assoc-in db [:forms :round :edit :games]
                       (round/sr-pairing players rounds))})))


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


  (db/reg-event-fx
    :steamdating.rounds/drop-nth
    (fn drop-nth
      [{:keys [db]} [n]]
      (let [rounds (->> (get-in db [:tournament :rounds])
                        (round/drop-nth n))
            last-round-index (dec (count rounds))
            {page :page {page-round-index :n} :params} (get db :route)
            should-navigate? (and (= :rounds-nth page)
                                  (> page-round-index last-round-index))
            navigate-to (if (= 0 (count rounds))
                          "/rounds/all"
                          (str "/rounds/nth/" last-round-index))]
        {:db (assoc-in db [:tournament :rounds] rounds)
         :dispatch (when should-navigate?
                     [:steamdating.routes/navigate navigate-to])})))


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
    :<- [:steamdating.players/origins]
    :<- [:steamdating.rankings/players]
    :<- [:steamdating.rounds/players-opponents]
    (fn edit-sub
      [[form-state factions origins rankings opponents] _]
      (-> form-state
          (round/validate-pairings {:factions factions
                                    :opponents opponents
                                    :origins origins})
          (update :edit round/update-factions factions)
          (update :edit round/update-players-options rankings))))


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
      [[_ n filter] _]
      [(re-frame/subscribe [:steamdating.rounds/round n])
       (re-frame/subscribe [:steamdating.filters/pattern filter])
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
    :<- [:steamdating.rankings/ranking]
    :<- [:steamdating.filters/pattern :rounds-all]
    (fn players-results-sub
      [[rounds players pattern] _]
      (->> players
           (filter #(re-find pattern (:name %)))
           (mapv #(assoc %
                         :results (round/results-for-player (:name %) rounds)
                         :played-lists (round/lists-for-player (:name %) rounds))))))


  (re-frame/reg-sub
    :steamdating.rounds/players-scores
    :<- [:steamdating.rounds/rounds]
    :<- [:steamdating.players/names]
    (fn players-scores-sub
      [[rounds names] _]
      (round/total-scores-for-players names rounds)))


  (re-frame/reg-sub
    :steamdating.rounds/summary
    :<- [:steamdating.rounds/players-results]
    :<- [:steamdating.sorts/sort :rounds-all {:by [:name]}]
    (fn summary-sub
      [[results {:keys [by reverse]}] _]
      (as-> results $
        (sort-by #(let [value (get-in % by)]
                    (if (string? value)
                      (.toLowerCase value)
                      value)) $)
        (cond-> $
          reverse clojure.core/reverse)))))
