(ns steamdating.models.notification
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.services.debug :refer [debug?]]))


(spec/def :sd.notification.push/manager
  some?)


(spec/def :sd.notification.push/subscription
  some?)


(spec/def :sd.notification.push/tournament-id
  (spec/and string? not-empty))


(spec/def :sd.notification/push
  (spec/keys :opt-un [:sd.notification.push/manager
                      :sd.notification.push/subscription
                      :sd.notification.push/tournament-id]))


(spec/def :sd.notification/notification
  (spec/keys :opt-un [:sd.notification/push]))



(defn check-push-subscription
  [push-manager]
  ;; (js/console.log "check-push-subscription")
  (-> (.getSubscription push-manager)
      (.then (fn [subscription]
               ;; (js/console.log "check-push-subscription" subscription)
               (when (some? subscription)
                 (re-frame/dispatch [:sd.notification.push.subscription/set subscription]))))
      (.catch (fn [error]
                (js/console.error "error check-push-subscription" error)
                (re-frame/dispatch [:sd.toaster/set
                                    {:type :error
                                     :message "Check push subscription failed"}])))))


(defn url-base64->uint8-array
  [base64-string]
  (let [padding (.repeat "=" (mod (- 4 (mod (count base64-string) 4)) 4))
        base64 (-> (str base64-string padding)
                   (s/replace "-" "+")
                   (s/replace "_" "/"))
        raw-data (.atob js/window base64)
        output-array (js/Uint8Array. (count raw-data))]
    (dotimes [n (count raw-data)]
      (aset output-array n (.charCodeAt raw-data n)))
    output-array))


(def vapid-public-key
  (if debug?
    "BOywB4CjnBpex4l5hwOlbGyEB1IwIaSXYJFl8BI5NcDaPWONawH0M5zujeW5ckwLhPel8BVCyxUdrli79fyXVEM"
    "BG9H7HrQAfXRsevpkR3rkAuK8erMh3puvVjNCggJDG-9akf0KTHLBG9_FJV6ijQmHw-pzyT9x1KChngQ0OX5Dx8"))


(def converted-vapid-key
  (url-base64->uint8-array vapid-public-key))


(defn create-push-subscription
  [push-manager]
  ;; (js/console.log "create-push-subscription")
  (-> (.getSubscription push-manager)
      (.then
        #(when (some? %) (.unsubscribe %)))
      (.then
        #(.subscribe push-manager #js {:userVisibleOnly true
                                       :applicationServerKey converted-vapid-key}))
      (.then
        (fn [subscription]
          ;; (js/console.log "create-push-subscription ok" (js/JSON.stringify subscription))
          (when (some? subscription)
            (re-frame/dispatch [:sd.notification.push.subscription/upload subscription]))))
      (.catch
        (fn [error]
          (js/console.error "create-push-subscription error" error)
          (re-frame/dispatch [:sd.toaster/set
                              {:type :error
                               :message "Create subscription failed"}])))))


(defn upload-subscription
  [api-url id subscription]
  {:method :post
   :uri (str api-url "/tournaments/" id "/notifications")
   :format (ajax/json-request-format)
   :params (.toJSON subscription)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:sd.notification.push.subscription/upload-success id subscription]
   :on-failure [:sd.toaster/set
                {:type :error
                 :message "Online subscription failed"}]})
