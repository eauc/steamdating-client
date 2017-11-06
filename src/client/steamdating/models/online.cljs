(ns steamdating.models.online
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.models.filter]
            [steamdating.services.debug :refer [debug?]]))


;; (spec/def :sd.online.push/manager
;;   some?)


;; (spec/def :sd.online.push/subscription
;;   some?)


;; (spec/def :sd.online/push
;;   (spec/keys :opt-un [:sd.online.push/manager
;;                       :sd.online.push/subscription]))


(spec/def :sd.online.user.auth/token
  (spec/and string? not-empty))


(spec/def :sd.online.user/auth
  (spec/keys :opt-un [:sd.online.user.auth/token]))


(spec/def :sd.online/user
  (spec/keys :opt-un [:sd.online.user/auth]))


(spec/def :sd.online.user/status-sub
  #{:logged :not-logged})


;; (spec/def :sd.online/show-follow
;;   boolean?)


(spec/def :sd.online.tournament/_id
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/updatedAt
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/name
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/date
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/user
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/link
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/edit
  (spec/keys :req-un [:sd.online.tournament/name
                      :sd.online.tournament/date]))


(spec/def :sd.online/tournament
  (spec/keys :req-un [:sd.online.tournament/_id
                      :sd.online.tournament/updatedAt
                      :sd.online.tournament/name
                      :sd.online.tournament/date
                      :sd.online.tournament/user
                      :sd.online.tournament/link]))


(spec/def :sd.online.tournaments/list
  (spec/coll-of :sd.online/tournament :kind vector?))


(spec/def :sd.online.tournaments/status
  #{:init :loading :success :error})


(spec/def :sd.online/tournaments
  (spec/keys :opt-un [:sd.online.tournaments/list
                      :sd.online.tournaments/status]))


(spec/def :sd.online.tournaments/filter
  :sd.filter/value)


(spec/def :sd.online/tournaments-sub
  (spec/keys :opt-un [:sd.online.tournaments/list
                      :sd.online.tournaments/status
                      :sd.online.tournaments/filter
                      :sd.sort/sort]))


(spec/def :sd.online/online
  (spec/keys :opt-un [:sd.online/user
                      :sd.online/tournaments]))


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
   :on-success [:sd.online.tournament/load-success confirm?]
   :on-failure [:sd.toaster/set
                {:type :error
                 :message "Failed to load tournament"}]})


(defn load-tournaments-request
  [{:keys [token]}]
  {:method :get
   :uri (str api-url "/tournaments/mine")
   :headers {"Authorization" (str "Bearer " token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:sd.online.tournaments/load-success]
   :on-failure [:sd.online.tournaments/load-error]})


(defn tournament-match-pattern?
  [tournament pattern]
  (some?
    (first
      (filter
        (fn [[k v]]
          (re-find pattern (str v)))
        tournament))))


(defn tournaments-filter-with
  [pattern tournaments]
  (vec (filter #(tournament-match-pattern? % pattern) tournaments)))


(defn tournaments-sort-with
  [{:keys [by reverse?]} tournaments]
  (as-> tournaments $
    (sort-by (juxt by :date) $)
    (cond-> $ reverse? (reverse))
    (vec $)))


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
     :on-success [:sd.online.tournament/upload-success]
     :on-failure [:sd.toaster/set
                  {:type :error
                   :message "Failed to upload current tournament"}]}))


;; (defn check-push-subscription
;;   [push-manager]
;;   ;; (js/console.log "check-push-subscription")
;;   (-> (.getSubscription push-manager)
;;       (.then (fn [subscription]
;;                ;; (js/console.log "check-push-subscription" subscription)
;;                (when (some? subscription)
;;                  (re-frame/dispatch [:sd.online.push/set-subscription subscription]))))
;;       (.catch (fn [error]
;;                 (js/console.error "error check-push-subscription" error)
;;                 (re-frame/dispatch [:sd.toaster/set
;;                                     {:type :error
;;                                      :message "Check push subscription failed"}])))))


;; (defn url-base64->uint8-array
;;   [base64-string]
;;   (let [padding (.repeat "=" (mod (- 4 (mod (count base64-string) 4)) 4))
;;         base64 (-> (str base64-string padding)
;;                    (s/replace "-" "+")
;;                    (s/replace "_" "/"))
;;         raw-data (.atob js/window base64)
;;         output-array (js/Uint8Array. (count raw-data))]
;;     (dotimes [n (count raw-data)]
;;       (aset output-array n (.charCodeAt raw-data n)))
;;     output-array))


;; (def vapid-public-key
;;   (if debug?
;;     "BOywB4CjnBpex4l5hwOlbGyEB1IwIaSXYJFl8BI5NcDaPWONawH0M5zujeW5ckwLhPel8BVCyxUdrli79fyXVEM"
;;     "BG9H7HrQAfXRsevpkR3rkAuK8erMh3puvVjNCggJDG-9akf0KTHLBG9_FJV6ijQmHw-pzyT9x1KChngQ0OX5Dx8"))


;; (def converted-vapid-key
;;   (url-base64->uint8-array vapid-public-key))


;; (defn create-push-subscription
;;   [push-manager]
;;   ;; (js/console.log "create-push-subscription")
;;   (-> (.getSubscription push-manager)
;;       (.then
;;         #(when (some? %) (.unsubscribe %)))
;;       (.then
;;         #(.subscribe push-manager #js {:userVisibleOnly true
;;                                        :applicationServerKey converted-vapid-key}))
;;       (.then
;;         (fn [subscription]
;;           ;; (js/console.log "create-push-subscription ok" (js/JSON.stringify subscription))
;;           (when (some? subscription)
;;             (re-frame/dispatch [:sd.online.push/upload-subscription subscription]))))
;;       (.catch
;;         (fn [error]
;;           (js/console.error "create-push-subscription error" error)
;;           (re-frame/dispatch [:sd.toaster/set
;;                               {:type :error
;;                                :message "Create subscription failed"}])))))


;; (defn upload-subscription
;;   [id subscription]
;;   {:method :post
;;    :uri (str api-url "/tournaments/" id "/notifications")
;;    :format (ajax/json-request-format)
;;    :params (.toJSON subscription)
;;    :response-format (ajax/json-response-format {:keywords? true})
;;    :on-success [:sd.online.push/upload-subscription-success subscription]
;;    :on-failure [:sd.toaster/set
;;                 {:type :error
;;                  :message "Online subscription failed"}]})
