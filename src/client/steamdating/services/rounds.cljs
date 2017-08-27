(ns steamdating.services.rounds
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.services.db :as db]
            [steamdating.models.round :as round]))

(trace-forms
  {:tracer (tracer :color "green")}

  (db/reg-event-fx
    :steamdating.rounds/start-next
    (fn start-next
      [{:keys [db]} _]
      (let [players (get-in db [:tournament :players])]
        {:dispatch-n [[:steamdating.forms/reset :round (round/->round players)]
                      [:steamdating.routes/navigate "/rounds/next"]]})))


  (db/reg-event-fx
    :steamdating.rounds/update-edit-player
    (fn update-edit-player
      [{:keys [db]} [field name]]
      {:db (update-in db [:forms :round :edit] round/pair-player field name)}))


  (db/reg-event-fx
    :steamdating.rounds/create-next
    (fn start-next
      [{:keys [db]} _]
      (let [new-round (get-in db [:forms :round :edit])]
        {:db (update-in db [:tournament :rounds] conj new-round)
         :dispatch [:steamdating.routes/navigate
                    (str "/rounds/nth/" (count (get-in db [:tournament :rounds])))]})))


  (re-frame/reg-sub
    :steamdating.rounds/edit
    :<- [:steamdating.forms/validate :round round/validate]
    :<- [:steamdating.players/factions]
    (fn edit-sub
      [[form-state factions] _]
      (-> form-state
          (round/validate-pairings factions)
          (update :edit round/update-factions factions)
          (update :edit round/update-players-options))))


  (re-frame/reg-sub
    :steamdating.rounds/rounds
    (fn rounds-sub
      [db [_ n]]
      (get-in db [:tournament :rounds])))


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
       (re-frame/subscribe [:steamdating.players/factions])])
    (fn round-view-sub
      [[round factions] _]
      (round/update-factions round factions)))

  )
