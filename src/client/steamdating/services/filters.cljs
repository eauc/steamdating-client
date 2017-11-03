(ns steamdating.services.filters
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.filter :as filter]
            [steamdating.services.db :as db]))

(db/reg-event-fx
  :sd.filters/set
  [(re-frame/path :filters)]
  (fn set
    [{:keys [db]} [field value]]
    {:db (assoc db field value)}))


(defn filter-sub
  [db [_ field]]
  {:pre [(spec/valid? :sd.db/db db)]
   :post [(spec/valid? :sd.filter/name field)]}
  (get-in db [:filters field]))

(re-frame/reg-sub
  :sd.filters/filter
  filter-sub)
