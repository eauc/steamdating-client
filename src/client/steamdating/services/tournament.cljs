(ns steamdating.services.tournament
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.services.db :as db]
            [steamdating.services.file]))


;; (trace-forms
;;   {:tracer (tracer :color "green")}

(db/reg-event-fx
  :steamdating.tournament/confirm-set
  (fn confirm-set
    [_ [value]]
    {:dispatch
     [:steamdating.prompt/set
      {:type :confirm
       :message "All previous data will be replaced. You sure ?"
       :on-validate [:steamdating.tournament/set value]}]}))


(db/reg-event-fx
  :steamdating.tournament/set
  [(re-frame/path :tournament)]
  (fn set
    [_ [value]]
    {:db value
     :dispatch [:steamdating.online/clear-current-edit]}))


(db/reg-event-fx
  :steamdating.tournament/open-file
  (fn open-file
    [_ [file]]
    {:steamdating.file/open
     {:file file
      :on-success [:steamdating.tournament/open-file-success]
      :on-failure [:steamdating.toaster/set
                   {:type :error
                    :message "Failed to open tournament file"}]}}))


(db/reg-event-fx
  :steamdating.tournament/open-file-success
  (fn open-file-success
    [_ [tournament]]
    {:dispatch-n
     [[:steamdating.tournament/confirm-set tournament]
      [:steamdating.toaster/set
       {:type :success
        :message "File loaded"}]]}))


(db/reg-event-fx
  :steamdating.tournament/settings-start-edit
  [(re-frame/path :tournament :settings)]
  (fn settings-start-edit
    [{settings :db} _]
    {:dispatch
     [:steamdating.forms/reset :settings settings]}))


(db/reg-event-fx
  :steamdating.tournament/settings-save
  (fn settings-save
    [{:keys [db]} _]
    (let [settings (get-in db [:forms :settings :edit])]
      {:db (assoc-in db [:tournament :settings] settings)})))


(re-frame/reg-sub
  :steamdating.tournament/tournament
  (fn tournament-sub
    [db]
    (get db :tournament)))


(re-frame/reg-sub
  :steamdating.tournament/online
  :<- [:steamdating.tournament/tournament]
  (fn tournament-online-sub
    [{:keys [online]}]
    online))

;; )
