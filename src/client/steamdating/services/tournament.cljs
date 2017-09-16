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
    {:db value}))


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


(re-frame/reg-sub
  :steamdating.tournament/tournament
  (fn tournament-sub
    [db]
    (get db :tournament)))

;; )
