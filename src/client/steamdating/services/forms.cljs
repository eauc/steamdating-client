(ns steamdating.services.forms
  (:require [re-frame.core :as re-frame]
            [steamdating.models.form :as form]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :steamdating.forms/reset
  [(re-frame/path :forms)]
  (fn forms-reset
    [{:keys [db]} [name value]]
    {:db (assoc db name (form/create value))}))


(db/reg-event-fx
  :steamdating.forms/update
  [(re-frame/path :forms)]
  (fn forms-reset
    [{:keys [db]} [name field value]]
    {:db (assoc-in db (into [name :edit] field) value)}))


(re-frame/reg-sub
  :steamdating.forms/form
  (fn form-sub
    [db [_ name]]
    (get-in db [:forms name])))


(re-frame/reg-sub
  :steamdating.forms/validate
  (fn form-validate-input[[_ name] _]
    (re-frame/subscribe [:steamdating.forms/form name]))
  (fn form-validate-sub
    [state [_ _ validation-fn]]
    (validation-fn state)))
