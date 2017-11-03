(ns steamdating.services.sorts
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.sort :as sort]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(db/reg-event-fx
  :sd.sorts/toggle
  [(re-frame/path :sorts)]
  (fn set
    [{:keys [db]} [key by defaut]]
    {:db (update db key sort/toggle-by by defaut)}))


(defn sort-sub
  [db [_ field def]]
  {:pre [(debug/spec-valid? :sd.db/db db)
         (debug/spec-valid? keyword? field)
         (debug/spec-valid? :sd.sort/sort def)]
   :post [(debug/spec-valid? :sd.sort/sort %)]}
  (get-in db [:sorts field] def))

(re-frame/reg-sub
  :sd.sorts/sort
  sort-sub)
