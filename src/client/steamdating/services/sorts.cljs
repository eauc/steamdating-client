(ns steamdating.services.sorts
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.services.db :as db]))


;; (trace-forms
;;   {:tracer (tracer :color "gold")}

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

;; )
