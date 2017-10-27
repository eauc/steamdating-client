(ns steamdating.services.forms
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.form :as form]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :sd.forms/reset
  [(re-frame/path :forms)]
  (fn reset
    [{:keys [db]} [name value]]
    {:db (assoc db name (form/create value))}))


(db/reg-event-fx
  :sd.forms/update
  [(re-frame/path :forms)]
  (fn update
    [{:keys [db]} [name field value]]
    {:db (update-in db [name] form/assoc-field field value)}))


(defn form-sub
  [db [_ name]]
  {:pre [(spec/valid? :sd.db/db db)
         (spec/valid? keyword? name)]
   :post [(spec/valid? :sd.form/form %)]}
  (get-in db [:forms name]))

(re-frame/reg-sub
  :sd.forms/form
  form-sub)


(defn validate-sub
  [state [_ _ validation-fn]]
  {:pre [(spec/valid? :sd.form/form state)
         (spec/valid? fn? validation-fn)]
   :post [(spec/valid? :sd.form/validated %)]}
  (validation-fn state))

(re-frame/reg-sub
  :sd.forms/validate
  (fn validate-sub-input[[_ name] _]
    (re-frame/subscribe [:sd.forms/form name]))
  validate-sub)
