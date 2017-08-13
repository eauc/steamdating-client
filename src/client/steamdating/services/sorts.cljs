(ns steamdating.services.sorts
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :steamdating.sorts/set
  [(re-frame/path :sorts)]
  (fn set
    [{:keys [db]} [key value]]
    {:db (assoc db key value)}))


(re-frame/reg-sub
  :steamdating.sorts/sort
  (fn sort-sub
    [db [_ field def]]
    (or (get-in db [:sorts field]) def)))
