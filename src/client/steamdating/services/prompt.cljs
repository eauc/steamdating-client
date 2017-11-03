(ns steamdating.services.prompt
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(db/reg-event-fx
  :sd.prompt/set
  [(re-frame/path :prompt)]
  (fn set
    [_ [prompt]]
    {:db prompt}))


(db/reg-event-fx
  :sd.prompt/cancel
  (fn cancel
    [{:keys [db]}]
    (let [fx {:db (dissoc db :prompt)}
          on-cancel (get-in db [:prompt :on-cancel])]
      (cond-> fx
        on-cancel (assoc :dispatch on-cancel)))))


(db/reg-event-fx
  :sd.prompt/validate
  (fn validate
    [{:keys [db]}]
    (let [fx {:db (dissoc db :prompt)}
          on-validate (get-in db [:prompt :on-validate])
          value (get-in db [:prompt :value])]
      (cond-> fx
        on-validate (assoc :dispatch (conj on-validate value))))))


(db/reg-event-fx
  :sd.prompt/update-value
  [(re-frame/path [:prompt :value])]
  (fn update-value
    [_ [value]]
    {:db value}))


(defn prompt-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? (spec/nilable :sd.prompt/prompt) %)]}
  (:prompt db))

(re-frame/reg-sub
  :sd.prompt/prompt
  prompt-sub)
