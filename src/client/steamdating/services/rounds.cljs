(ns steamdating.services.rounds
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.models.round :as round]))


(db/reg-event-fx
  :steamdating.rounds/start-next
  (fn start-next
    [{:keys [db]} _]
    (let [players (get-in db [:tournament :players])]
      {:dispatch-n [[:steamdating.forms/reset :round (round/->round players)]
                    [:steamdating.routes/navigate "/rounds/next"]]})))


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
  :<- [:steamdating.forms/form :round round/validate]
  :<- [:steamdating.players/factions]
  (fn edit-sub
    [[form-state factions] _]
    (-> form-state
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