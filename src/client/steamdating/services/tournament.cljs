(ns steamdating.services.tournament
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :tournament-confirm-set
  (fn tournament-confirm-set [_ [value]]
    {:dispatch
     [:prompt-set
      {:type :confirm
       :message "All previous data will be replaced. You sure ?"
       :on-validate [:tournament-set value]}]}))


(db/reg-event-fx
  :tournament-set
  [(re-frame/path :tournament)]
  (fn tournament-set [_ [value]]
    {:db value}))
