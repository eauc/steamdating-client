(ns steamdating.components.prompt.handler
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :steamdating.prompt/set
  [(re-frame/path :prompt)]
  (fn prompt-set
    [_ [prompt]]
    {:db prompt}))


(db/reg-event-fx
  :steamdating.prompt/cancel
  [(re-frame/path :prompt)]
  (fn prompt-cancel
    [{:keys [db]}]
    (let [fx {:db nil}
          on-cancel (:on-cancel db)]
      (cond-> fx
        on-cancel (assoc :dispatch on-cancel)))))


(db/reg-event-fx
  :steamdating.prompt/validate
  [(re-frame/path :prompt)]
  (fn prompt-validate
    [{:keys [db]}]
    (let [fx {:db nil}
          on-validate (:on-validate db)
          value (:value db)]
      (cond-> fx
        on-validate (assoc :dispatch (conj on-validate value))))))


(db/reg-event-fx
  :steamdating.prompt/update
  [(re-frame/path :prompt)]
  (fn prompt-update
    [{:keys [db]} [value]]
    {:db (assoc db :value value)}))
