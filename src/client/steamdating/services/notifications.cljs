(ns steamdating.services.notifications
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.notification :as notification]
            [steamdating.models.online :as online]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug :refer [debug?]]
            [steamdating.services.online]
            [steamdating.services.tournament]
            [steamdating.models.online :as online]))


(db/reg-event-fx
  :sd.notification.push.manager/set
  [(re-frame/path :online :notification :push :manager)]
  (fn push-manager-set
    [_ [manager]]
    {:db manager
     :sd.notification.push.subscription/check manager}))


(re-frame/reg-fx
  :sd.notification.push.subscription/check
  (fn push-subscription-check-fx
    [manager]
    (notification/check-push-subscription manager)))


(db/reg-event-fx
  :sd.notification.push.subscription/set
  [(re-frame/path :online :notification :push :subscription)]
  (fn push-subscription-set
    [_ [subscription]]
    {:db subscription}))


(db/reg-event-fx
  :sd.notification.push.subscription/create
  [(re-frame/path :online :notification :push :manager)]
  (fn push-subscription-create
    [{manager :db} _]
    {:sd.notification.push.subscription/create manager}))


(re-frame/reg-fx
  :sd.notification.push.subscription/create
  (fn push-subscription-create-fx
    [manager]
    (notification/create-push-subscription manager)))


(db/reg-event-fx
  :sd.notification.push.subscription/upload
  (fn push-subscription-upload
    [{:keys [db]} [subscription]]
    (let [id (get-in db [:tournament :online :_id])]
      {:http-xhrio (notification/upload-subscription
                     online/api-url id subscription)})))


(db/reg-event-fx
  :sd.notification.push.subscription/upload-success
  [(re-frame/path :online :notification :push :tournament-id)]
  (fn push-subscription-upload-success
    [_ [id subscription]]
    {:db id
     :dispatch-n [[:sd.notification.push.subscription/set subscription]
                 [:sd.toaster/set
                  {:type :success
                   :message "Notification subscription success"}]]}))


(defn push-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? (spec/nilable :sd.notification/push) %)]}
  (get-in db [:online :notification :push]))

(re-frame/reg-sub
  :sd.notification/push
  push-sub)


(defn push-status-sub
  [[{:keys [manager subscription tournament-id] :as push}
    {:keys [_id name] :as tournament-online}]]
  {:pre [(debug/spec-valid? (spec/nilable :sd.notification/push) push)
         (debug/spec-valid? (spec/nilable :sd.tournament/online) tournament-online)]
   :post [(debug/spec-valid? :sd.notification/push-sub %)]}
  (let [can-subscribe? (some? manager)
        has-subscribed? (and (some? subscription)
                             (= _id tournament-id))]
    {:name name
     :can-subscribe? can-subscribe?
     :has-subscribed? has-subscribed?}))

(re-frame/reg-sub
  :sd.notifications.push/status
  :<- [:sd.notification/push]
  :<- [:sd.tournament/online]
  push-status-sub)
