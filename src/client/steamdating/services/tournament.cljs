(ns steamdating.services.tournament
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.file]))


(db/reg-event-fx
  :sd.tournament/confirm-set
  (fn confirm-set
    [_ [value]]
    {:dispatch
     [:sd.prompt/set
      {:type :confirm
       :message "All current data will be replaced.\nYou sure ?"
       :on-validate [:sd.tournament/set value]}]}))


(db/reg-event-fx
  :sd.tournament/set
  [(re-frame/path :tournament)]
  (fn set
    [_ [value]]
    {:db value
     :dispatch [:sd.forms/reset :online-tournament (or (get value :online) {})]}))


(db/reg-event-fx
  :sd.tournament.file/open
  (fn file-open
    [_ [file]]
    {:sd.file/open
     {:file file
      :on-success [:sd.tournament.file/open-success]
      :on-failure [:sd.toaster/set
                   {:type :error
                    :message "Failed to open tournament file"}]}}))


(db/reg-event-fx
  :sd.tournament.file/open-success
  (fn file-open-success
    [_ [tournament]]
    {:dispatch-n
     [[:sd.tournament/confirm-set tournament]
      [:sd.toaster/set
       {:type :success
        :message "File loaded"}]]}))


;; (db/reg-event-fx
;;   :steamdating.tournament/settings-start-edit
;;   [(re-frame/path :tournament :settings)]
;;   (fn settings-start-edit
;;     [{settings :db} _]
;;     {:dispatch
;;      [:steamdating.forms/reset :settings settings]}))


;; (db/reg-event-fx
;;   :steamdating.tournament/settings-save
;;   (fn settings-save
;;     [{:keys [db]} _]
;;     (let [settings (get-in db [:forms :settings :edit])]
;;       {:db (assoc-in db [:tournament :settings] settings)})))


(defn tournament-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? :sd.tournament/tournament %)]}
  (get db :tournament))

(re-frame/reg-sub
  :sd.tournament/tournament
  tournament-sub)


;; (re-frame/reg-sub
;;   :steamdating.tournament/online
;;   :<- [:steamdating.tournament/tournament]
;;   (fn tournament-online-sub
;;     [{:keys [online]}]
;;     online))
