(ns steamdating.services.prompt
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.services.db :as db]))


;; (trace-forms
;;   {:tracer (tracer :color "brown")}

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


(re-frame/reg-sub
  :steamdating.prompt/prompt
  (fn prompt-sub
    [db _]
    (:prompt db)))

;; )
