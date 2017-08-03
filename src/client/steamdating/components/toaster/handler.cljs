(ns steamdating.components.toaster.handler
  (:require [steamdating.services.db :as db]
            [re-frame.core :as re-frame]))


(defonce timeout
  (atom nil))


(re-frame/reg-fx
  :toaster-timeout
  (fn []
    (when @timeout (js/clearTimeout @timeout))
    (reset! timeout (js/setTimeout #(re-frame/dispatch [:toaster-clear]) 1000))))


(db/reg-event-fx
  :toaster-set
  [(re-frame/path :toaster)]
  (fn toaster-set
    [_ [toaster]]
    {:db toaster
     :toaster-timeout nil}))


(db/reg-event-fx
  :toaster-clear
  [(re-frame/path :toaster)]
  (fn toaster-clear
    []
    {:db nil}))
