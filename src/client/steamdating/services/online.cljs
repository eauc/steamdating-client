(ns steamdating.services.online
  (:require [ajax.core :as ajax]
            [cljs.loader :as loader]
            [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.form :as form]
            [steamdating.models.online :as online]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug :refer [debug?]]))


(re-frame/reg-fx
  :sd.auth/show-login
  (fn show-login
    []
    (loader/load
      :auth
      (fn []
        ((resolve 'steamdating.services.auth/show-login)
         {:client-id online/client-id
          :domain online/domain
          :on-authenticated #(re-frame/dispatch
                               [:sd.online/login-success
                                (aget % "idToken")])})))))


(db/reg-event-fx
  :sd.online/login
  (fn login
    []
    {:sd.auth/show-login nil}))


(db/reg-event-fx
  :sd.online/logout
  [(re-frame/path :online :user)]
  (fn logout
    [{:keys [db]} _]
    {:db (dissoc db :auth :tournaments)}))


;; (db/reg-event-fx
;;   :sd.online/error-logout
;;   [(re-frame/path :online)]
;;   (fn logout
;;     [_ [message {:keys [status]}]]
;;     {:dispatch-n [(when (= 401 status) [:sd.online/logout])
;;                   [:sd.toaster/set
;;                    {:type :error :message message}]]}))


(db/reg-event-fx
  :sd.online/login-success
  [(re-frame/path :online :user :auth :token)]
  (fn login-success
    [{:keys [db]} [token]]
    {:db token}))


(when debug/debug?
  (defn test-login
    [token]
    (re-frame/dispatch [:sd.online/login-success token])))


;; (db/reg-event-fx
;;   :sd.online/toggle-show-follow
;;   [(re-frame/path :online :show-follow)]
;;   (fn clear-current-edit
;;     [{:keys [db]} _]
;;     {:db (not db)}))


;; (db/reg-event-fx
;;   :sd.online/load-tournament
;;   [(re-frame/path :online)]
;;   (fn load-tournament
;;     [{:keys [db]} [link confirm?]]
;;     {:http-xhrio (online/load-tournament-request link confirm?)}))


;; (db/reg-event-fx
;;   :sd.online/load-tournament-success
;;   [(re-frame/path :tournament)]
;;   (fn load-tournament-success
;;     [{:keys [db]} [confirm? {:keys [tournament] :as info}]]
;;     (let [new-tournament (assoc tournament :online (dissoc info :tournament))
;;           valid? (spec/valid? :sd.tournament/tournament new-tournament)]
;;       (if valid?
;;         {:dispatch-n [(if confirm?
;;                         [:sd.tournament/confirm-set new-tournament]
;;                         [:sd.tournament/set new-tournament])
;;                       [:sd.toaster/set
;;                        {:type :success
;;                         :message "Online tournament loaded"}]]}
;;         {:dispatch [:sd.toaster/set
;;                     {:type :error
;;                      :message "Failed to load online tournament"}]}))))


;; (db/reg-event-fx
;;   :sd.online/load-tournaments
;;   [(re-frame/path :online)]
;;   (fn load-tournaments
;;     [{:keys [db]} _]
;;     {:http-xhrio (online/load-tournaments-request (:token db))}))


;; (db/reg-event-fx
;;   :sd.online/load-tournaments-success
;;   [(re-frame/path :online :tournaments)]
;;   (fn load-tournaments-success
;;     [{:keys [db]} [{:keys [tournaments]}]]
;;     {:db tournaments}))


;; (db/reg-event-fx
;;   :sd.online/load-tournaments-error
;;   [(re-frame/path :online)]
;;   (fn load-tournaments-success
;;     [{:keys [db]} _]
;;     {:db (assoc db :tournaments :failed)
;;      :dispatch [:sd.toaster/set
;;                 {:type :error
;;                  :message "Failed to load online tournaments"}]}))


;; (db/reg-event-fx
;;   :sd.online/upload-current
;;   (fn upload-current
;;     [{:keys [db]} _]
;;     (let [token (get-in db [:online :token])
;;           tournament (get db :tournament)
;;           online (get-in db [:forms :online :edit])]
;;       {:http-xhrio (online/upload-tournament-request token online tournament)})))


;; (db/reg-event-fx
;;   :sd.online/clear-current-edit
;;   [(re-frame/path :forms)]
;;   (fn clear-current-edit
;;     [{:keys [db]} _]
;;     {:db (dissoc db :online)}))


;; (db/reg-event-fx
;;   :sd.online/upload-current-success
;;   [(re-frame/path :tournament :online)]
;;   (fn upload-current-success
;;     [_ [info]]
;;     {:db info
;;      :dispatch-n [[:sd.online/clear-current-edit]
;;                   [:sd.online/load-tournaments]
;;                   [:sd.toaster/set
;;                    {:type :success
;;                     :message "Tournament uploaded"}]]}))


;; (db/reg-event-fx
;;   :sd.online.follow/refresh
;;   [(re-frame/path [:route :params :id])]
;;   (fn follow-refresh
;;     [{id :db}]
;;     {:dispatch [:sd.online/load-tournament (str "/tournaments/" id) false]}))


;; (db/reg-event-fx
;;   :sd.online.push/set-manager
;;   [(re-frame/path :online :push :manager)]
;;   (fn push-set-manager
;;     [_ [manager]]
;;     {:db manager
;;      :sd.online.push/check-subscription manager}))


;; (re-frame/reg-fx
;;   :sd.online.push/check-subscription
;;   (fn push-check-subscription-fx
;;     [manager]
;;     (online/check-push-subscription manager)))


;; (db/reg-event-fx
;;   :sd.online.push/set-subscription
;;   [(re-frame/path :online :push :subscription)]
;;   (fn push-set-subscription
;;     [_ [subscription]]
;;     {:db subscription}))


;; (db/reg-event-fx
;;   :sd.online.push/create-subscription
;;   [(re-frame/path :online :push :manager)]
;;   (fn push-create-subscription
;;     [{manager :db} _]
;;     {:sd.online.push/create-subscription manager}))


;; (re-frame/reg-fx
;;   :sd.online.push/create-subscription
;;   (fn push-create-subscription-fx
;;     [manager]
;;     (online/create-push-subscription manager)))


;; (db/reg-event-fx
;;   :sd.online.push/upload-subscription
;;   (fn upload-subscription
;;     [{:keys [db]} [subscription]]
;;     (let [id (get-in db [:tournament :online :_id])]
;;       {:http-xhrio (online/upload-subscription id subscription)})))


;; (db/reg-event-fx
;;   :sd.online.push/upload-subscription-success
;;   (fn upload-subscription
;;     [{:keys [db]} [subscription]]
;;     {:dispatch-n [[:sd.online.push/set-subscription subscription]
;;                   [:sd.toaster/set
;;                    {:type :success
;;                     :message "Online subscription success"}]]}))


(defn user-auth-token-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? (spec/nilable :sd.online.user.auth/token) %)]}
  (get-in db [:online :user :auth :token]))

(re-frame/reg-sub
  :sd.online.user.auth/token
  user-auth-token-sub)


(defn user-status-sub
  [[token]]
  {:pre [(debug/spec-valid? (spec/nilable :sd.online.user.auth/token) token)]
   :post [(debug/spec-valid? :sd.online.user/status-sub %)]}
  (if (some? token)
    :logged
    :not-logged))

(re-frame/reg-sub
  :sd.online.user/status
  :<- [:sd.online.user.auth/token]
  user-status-sub)


;; (re-frame/reg-sub
;;   :sd.online/status
;;   :<- [:sd.online/online]
;;   :<- [:sd.tournament/online]
;;   (fn status-sub
;;     [[{:keys [token]} {:keys [link]}]]
;;     (case [(some? token) (some? link)]
;;       [true true] :synced
;;       [true false] :online
;;       [false false] :offline
;;       [false true] :offline)))


;; (re-frame/reg-sub
;;   :sd.online/tournaments
;;   :<- [:sd.online/online]
;;   (fn tournaments-sub
;;     [{:keys [token tournaments]}]
;;     (if (some? tournaments)
;;       (if (= :failed tournaments)
;;         {:status :failed
;;          :tournaments []}
;;         {:status :loaded
;;          :tournaments (reverse (sort-by :date tournaments))})
;;       (if (some? token)
;;         (do
;;           (re-frame/dispatch [:sd.online/load-tournaments])
;;           {:status :loading
;;            :tournaments []})
;;         {:status :offline
;;          :tournaments []}))))


;; (re-frame/reg-sub
;;   :sd.online/edit-current
;;   (fn edit-current-sub
;;     [{:keys [forms tournament]}]
;;     (let [current-online (get tournament :online {})
;;           form-state {:base current-online
;;                       :edit (merge current-online (get-in forms [:online :edit]))}]
;;       (form/validate form-state :sd.online/edit))))


;; (re-frame/reg-sub
;;   :sd.online/show-follow
;;   :<- [:sd.online/online]
;;   (fn follow-sub
;;     [{:keys [show-follow]}]
;;     show-follow))


;; (re-frame/reg-sub
;;   :sd.online/follow-status
;;   :<- [:sd.online/status]
;;   :<- [:sd.online/show-follow]
;;   :<- [:sd.tournament/online]
;;   (fn follow-status-sub
;;     [[status show-follow {:keys [_id name]}]]
;;     (let [url (str (.-origin js/location) "/#/follow/" _id)
;;           show? (and (= :synced status) show-follow)]
;;       {:show? show?
;;        :status status
;;        :name name
;;        :url url})))


;; (re-frame/reg-sub
;;   :sd.online/push
;;   :<- [:sd.online/online]
;;   :<- [:sd.tournament/online]
;;   (fn push-sub [[{:keys [push]} {:keys [name]}]]
;;     (let [{:keys [manager subscription]} push
;;           can-subscribe? (some? manager)
;;           has-subscribed? (some? subscription)]
;;       {:name name
;;        :can-subscribe? can-subscribe?
;;        :has-subscribed? has-subscribed?})))
