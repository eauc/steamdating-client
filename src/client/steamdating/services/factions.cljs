(ns steamdating.services.factions
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [day8.re-frame.http-fx]
            [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(db/reg-event-fx
  :sd.factions/initialize
  (fn init
    []
    {:http-xhrio {:method :get
                  :uri "/data/factions.json"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:sd.factions/set]
                  :on-failure [:sd.toaster/set
                               {:type :error
                                :message "Failed to load factions"}]}}))


(db/reg-event-fx
  :sd.factions/set
  [(re-frame/path :factions)]
  (fn set
    [_ [factions]]
    {:db factions}))


(defn factions-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? :sd.faction/factions %)]}
  (:factions db))

(re-frame/reg-sub
  :sd.factions/factions
  factions-sub)


(defn names-sub
  [factions]
  {:pre [(debug/spec-valid? :sd.faction/factions factions)]
   :post [(debug/spec-valid? :sd.faction/names %)]}
  (faction/names factions))

(re-frame/reg-sub
  :sd.factions/names
  :<- [:sd.factions/factions]
  names-sub)


(defn icons-sub
  [factions]
  {:pre [(debug/spec-valid? :sd.faction/factions factions)]
   :post [(debug/spec-valid? :sd.faction/icons %)]}
  (faction/icons factions))

(re-frame/reg-sub
  :sd.factions/icons
  :<- [:sd.factions/factions]
  icons-sub)
