(ns steamdating.services.players
  (:require [steamdating.services.db :as db]))


(db/reg-event-fx
  :steamdating.players/start-create
  (fn players-start-create
    []
    {:dispatch-n [[:steamdating.forms/reset :player {}]
                  [:steamdating.routes/navigate "/players/create"]]}))
