(ns steamdating.services.spinner
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.spinner]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug :refer [debug?]]))


(defonce timeout
  (atom nil))


(re-frame/reg-fx
  :sd.spinner/timeout
  (fn [ms]
    (when @timeout (js/clearTimeout @timeout))
    (reset! timeout (js/setTimeout
                      #(re-frame/dispatch
                         [:sd.spinner/clear]) ms))))


(db/reg-event-fx
  :sd.spinner/set
  [(re-frame/path :spinner)]
  (fn set
    [_ [{:keys [message timeout]}]]
    {:db {:message message}
     :sd.spinner/timeout timeout}))


(db/reg-event-fx
  :sd.spinner/clear
  (fn clear
    [{:keys [db]}]
    {:db (dissoc db :spinner)}))


(defn spinner-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? (spec/nilable :sd.spinner/spinner) %)]}
  (:spinner db))

(re-frame/reg-sub
  :sd.spinner/spinner
  spinner-sub)
