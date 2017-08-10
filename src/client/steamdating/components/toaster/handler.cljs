(ns steamdating.components.toaster.handler
  (:require [steamdating.services.db :as db]
            [re-frame.core :as re-frame]))


(defonce timeout
  (atom nil))


(re-frame/reg-fx
  :steamdating.toaster/timeout
  (fn []
    (when @timeout (js/clearTimeout @timeout))
    (reset! timeout (js/setTimeout
                      #(re-frame/dispatch
                         [:steamdating.toaster/clear]) 1000))))


(db/reg-event-fx
  :steamdating.toaster/set
  [(re-frame/path :toaster)]
  (fn toaster-set
    [_ [toaster]]
    {:db toaster
     :steamdating.toaster/timeout nil}))


(db/reg-event-fx
  :steamdating.toaster/clear
  [(re-frame/path :toaster)]
  (fn toaster-clear
    []
    {:db nil}))
