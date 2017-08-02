(ns steamdating.components.prompt.handler
  (:require [steamdating.db :as db]
            [re-frame.core :as re-frame]))


(db/reg-event-fx
  :prompt-cancel
  [(re-frame/path :prompt)]
  (fn prompt-cancel
    [{:keys [db]}]
    (let [fx {:db nil}
          on-cancel (:on-cancel db)]
      (cond-> fx
        on-cancel (assoc :dispatch on-cancel)))))


(db/reg-event-fx
  :prompt-validate
  [(re-frame/path :prompt)]
  (fn prompt-validate
    [{:keys [db]}]
    (let [fx {:db nil}
          on-validate (:on-validate db)
          value (:value db)]
      (cond-> fx
        on-validate (assoc :dispatch (conj on-validate value))))))


(db/reg-event-fx
  :prompt-update
  [(re-frame/path :prompt)]
  (fn prompt-update
    [{:keys [db]} [value]]
    {:db (assoc db :value value)}))
