(ns steamdating.services.players
  (:require [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.models.player :as player]
            [steamdating.services.db :as db]))

(db/reg-event-fx
  :steamdating.players/start-create
  (fn players-start-create
    []
    {:dispatch-n [[:steamdating.forms/reset :player {}]
                  [:steamdating.routes/navigate "/players/create"]]}))


(db/reg-event-fx
  :steamdating.players/create-current-edit
  [(re-frame/path [:forms :player :edit])]
  (fn create-current-edit
    [{:keys [db]}]
    {:dispatch-n [[:steamdating.players/create db]
                  [:steamdating.routes/back]]}))


(db/reg-event-fx
  :steamdating.players/create
  [(re-frame/path [:tournament :players])]
  (fn create
    [{:keys [db]} [player]]
    {:db (player/add db player)}))


(re-frame/reg-sub
  :steamdating.players/list
  (fn players-list
    [db _]
    (get-in db [:tournament :players])))


(re-frame/reg-sub
  :steamdating.players/edit-casters
  (fn edit-casters-input
    []
    [(re-frame/subscribe [:steamdating.factions/factions])
     (re-frame/subscribe [:steamdating.forms/form :player])])
  (fn edit-casters
    [[factions {:keys [edit] :as form}] _]
    (let [edit-faction (:faction edit)]
      (faction/casters factions edit-faction))))
