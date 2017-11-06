(ns steamdating.services.tournament
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.file]
            [steamdating.services.routes]))


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


(db/reg-event-fx
  :sd.tournament.settings/start-edit
  [(re-frame/path :tournament :settings)]
  (fn settings-start-edit
    [{settings :db} _]
    {:dispatch
     [:sd.forms/reset :settings settings]}))


(db/reg-event-fx
  :sd.tournament.settings/save
  (fn settings-save
    [{:keys [db]} _]
    (let [settings (get-in db [:forms :settings :edit])]
      {:db (assoc-in db [:tournament :settings] settings)
       :dispatch [:sd.routes/back]})))


(defn tournament-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? :sd.tournament/tournament %)]}
  (get db :tournament))

(re-frame/reg-sub
  :sd.tournament/tournament
  tournament-sub)


(defn online-sub
  [tournament]
  {:pre [(debug/spec-valid? :sd.tournament/tournament tournament)]
   :post [(debug/spec-valid? (spec/nilable :sd.tournament/online) %)]}
  (get tournament :online))

(re-frame/reg-sub
  :sd.tournament/online
  :<- [:sd.tournament/tournament]
  online-sub)
