(ns steamdating.models.online
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.services.debug :refer [debug?]]))


(spec/def :steamdating.online.push/manager
  some?)


(spec/def :steamdating.online.push/subscription
  some?)


(spec/def :steamdating.online/push
  (spec/keys :opt-un [:steamdating.online.push/manager
                      :steamdating.online.push/subscription]))


(spec/def :steamdating.online/token
  (spec/and string? not-empty))


(spec/def :steamdating.online/show-follow
  boolean?)


(spec/def :steamdating.online.tournament/_id
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/updatedAt
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/name
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/date
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/user
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/link
  (spec/and string? not-empty))


(spec/def :steamdating.online/edit
  (spec/keys :req-un [:steamdating.online.tournament/name
                      :steamdating.online.tournament/date]))


(spec/def :steamdating.online/tournament
  (spec/keys :req-un [:steamdating.online.tournament/_id
                      :steamdating.online.tournament/updatedAt
                      :steamdating.online.tournament/name
                      :steamdating.online.tournament/date
                      :steamdating.online.tournament/user
                      :steamdating.online.tournament/link]))


(spec/def :steamdating.online/tournaments
  (spec/or :list (spec/coll-of :steamdating.online/tournament :kind vector?)
           :error #{:failed}))


(spec/def :steamdating.online/online
  (spec/keys :opt-un [:steamdating.online/push
                      :steamdating.online/token
                      :steamdating.online/show-follow
                      :steamdating.online/tournaments]))


(def domain
  "eauc.eu.auth0.com")


(def client-id
  (if debug?
    "CKGG9bWf1UJvwTrU0Ya8V8tUCN7vK27C"
    "vBhy4C4dLSkuoTbmldLYNxULGTyz6swK"))


(def api-url
  (if debug?
    (str "http://" (.-hostname js/location) ":4001")
    "https://steamdating-data.herokuapp.com"))


(defn load-tournament-request
  [link confirm?]
  {:method :get
   :uri (str api-url link)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:steamdating.online/load-tournament-success confirm?]
   :on-failure [:steamdating.online/error-logout
                "Failed to load online tournament"]})


(defn load-tournaments-request
  [token]
  {:method :get
   :uri (str api-url "/tournaments/mine")
   :headers {"Authorization" (str "Bearer " token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:steamdating.online/load-tournaments-success]
   :on-failure [:steamdating.online/load-tournaments-error]})


(defn upload-tournament-request
  [token online tournament]
  (let [link (get-in tournament [:online :link])
        update? (some? link)
        data (-> (get tournament :online)
                 (merge online)
                 (select-keys [:name :date])
                 (assoc :tournament (dissoc tournament :online)))]
    {:method (if update? :put :post)
     :uri (str api-url (if update? link "/tournaments/mine"))
     :headers {"Authorization" (str "Bearer " token)}
     :format (ajax/json-request-format)
     :params data
     :response-format (ajax/json-response-format {:keywords? true})
     :on-success [:steamdating.online/upload-current-success]
     :on-failure [:steamdating.online/error-logout
                  "Failed to upload current tournament"]}))


(defn check-push-subscription
  [push-manager]
  ;; (js/console.log "check-push-subscription")
  (-> (.getSubscription push-manager)
      (.then (fn [subscription]
               ;; (js/console.log "check-push-subscription" subscription)
               (when (some? subscription)
                 (re-frame/dispatch [:steamdating.online.push/set-subscription subscription]))))
      (.catch (fn [error]
                (js/console.error "error check-push-subscription" error)
                (re-frame/dispatch [:steamdating.toaster/set
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
      (.then #(if (some? %)
                %
                (.subscribe push-manager #js {:userVisibleOnly true
                                              :applicationServerKey converted-vapid-key})))
      (.then (fn [subscription]
               ;; (js/console.log "create-push-subscription ok" (js/JSON.stringify subscription))
               (when (some? subscription)
                 (re-frame/dispatch [:steamdating.online.push/upload-subscription subscription]))))
      (.catch (fn [error]
                (js/console.error "create-push-subscription error" error)
                (re-frame/dispatch [:steamdating.toaster/set
                                    {:type :error
                                     :message "Create subscription failed"}])))))


(defn upload-subscription
  [id subscription]
  {:method :post
   :uri (str api-url "/tournaments/" id "/notifications")
   :format (ajax/json-request-format)
   :params (.toJSON subscription)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:steamdating.online.push/upload-subscription-success subscription]
   :on-failure [:steamdating.toaster/set
                {:type :error
                 :message "Online subscription failed"}]})
