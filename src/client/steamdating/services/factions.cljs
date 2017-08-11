(ns steamdating.services.factions
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.services.db :as db]))


(db/reg-event-fx
  :steamdating.factions/initialize
  (fn init
    []
    {:http-xhrio {:method :get
                  :uri "/data/factions.json"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:steamdating.factions/set]
                  :on-failure [:steamdating.toaster/set
                               {:type :error
                                :message "Failed to load factions"}]}}))


(db/reg-event-fx
  :steamdating.factions/set
  [(re-frame/path :factions)]
  (fn set
    [_ [factions]]
    {:db factions}))


(re-frame/reg-sub
  :steamdating.factions/factions
  (fn sub
    [db _]
    (:factions db)))


(re-frame/reg-sub
  :steamdating.factions/names
  :<- [:steamdating.factions/factions]
  (fn names-sub
    [factions _]
    (faction/names factions)))
