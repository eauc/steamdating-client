(ns steamdating.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.services.debug :as debug]
            [steamdating.services.db :as db]))


(defn mount-root
  []
  (re-frame/clear-subscription-cache!))


(defn ^:export init
  []
  (debug/setup)
  (re-frame/dispatch-sync [:steamdating.db/initialize]))
