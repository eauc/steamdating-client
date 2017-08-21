(ns steamdating.services.rounds
  (:require [steamdating.services.db :as db]
            [steamdating.models.round :as round]))


(db/reg-event-fx
  :steamdating.rounds/start-next
  (fn start-next
    [{:keys [db]} _]
    (let [players (get-in db [:tournament :players])]
      {:dispatch-n [[:steamdating.forms/reset :round (round/->round players)]
                    [:steamdating.routes/navigate "/rounds/next"]]})))
