(ns steamdating.services.online
  (:require [ajax.core :as ajax]
            [cljs.loader :as loader]
            [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.filter :as filter]
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


(db/reg-event-fx
  :sd.online/login-success
  [(re-frame/path :online)]
  (fn login-success
    [{:keys [db]} [token]]
    {:db (-> db
             (assoc-in [:user :auth :token] token)
             (dissoc :tournaments))}))


(when debug/debug?
  (defn test-login
    [token]
    (re-frame/dispatch [:sd.online/login-success token])))


(db/reg-event-fx
  :sd.online.follow/show
  [(re-frame/path :online :follow :show?)]
  (fn follow-show
    [{:keys [db]} _]
    {:db true}))


(db/reg-event-fx
  :sd.online.follow/hide
  [(re-frame/path :online :follow :show?)]
  (fn follow-hide
    [{:keys [db]} _]
    {:db false}))


(db/reg-event-fx
  :sd.online.tournament/load
  (fn tournament-load
    [_ [{:keys [link confirm?]}]]
    {:http-xhrio (online/load-tournament-request link confirm?)}))


(db/reg-event-fx
  :sd.online.tournament/load-success
  [(re-frame/path :tournament)]
  (fn tournament-load-success
    [{:keys [db]} [confirm? {:keys [tournament] :as info}]]
    (let [new-tournament (assoc tournament :online (dissoc info :tournament))
          valid? (spec/valid? :sd.tournament/tournament new-tournament)]
      (if valid?
        {:dispatch-n [(if confirm?
                        [:sd.tournament/confirm-set new-tournament]
                        [:sd.tournament/set new-tournament])
                      [:sd.toaster/set
                       {:type :success
                        :message "Online tournament loaded"}]]}
        {:dispatch [:sd.toaster/set
                    {:type :error
                     :message "Invalid tournament data"}]}))))


(db/reg-event-fx
  :sd.online.tournaments/load
  [(re-frame/path :online)]
  (fn tournaments-load
    [{:keys [db]} _]
    {:db (assoc-in db [:tournaments :status] :loading)
     :http-xhrio (online/load-tournaments-request
                   (get-in db [:user :auth]))}))


(db/reg-event-fx
  :sd.online.tournaments/load-success
  [(re-frame/path :online :tournaments)]
  (fn tournaments-load-success
    [{:keys [db]} [{:keys [tournaments]}]]
    {:db {:status :success
          :list tournaments}}))


(db/reg-event-fx
  :sd.online.tournaments/load-error
  [(re-frame/path :online :tournaments)]
  (fn tournaments-load-error
    [{:keys [db]} _]
    {:db {:status :error
          :list []}
     :dispatch [:sd.toaster/set
                {:type :error
                 :message "Failed to load online tournaments"}]}))


(db/reg-event-fx
  :sd.online.tournament/upload
  (fn tournament-upload
    [{:keys [db]} _]
    (let [token (get-in db [:online :user :auth :token])
          tournament (get db :tournament)
          online (get-in db [:forms :online-tournament :edit])]
      {:http-xhrio (online/upload-tournament-request token online tournament)})))


(db/reg-event-fx
  :sd.online.tournament/upload-success
  [(re-frame/path :tournament :online)]
  (fn tournament-upload-success
    [_ [info]]
    {:db info
     :dispatch-n [[:sd.forms/reset :online-tournament info]
                  [:sd.online.tournaments/load]
                  [:sd.toaster/set
                   {:type :success
                    :message "Tournament uploaded"}]]}))


(db/reg-event-fx
  :sd.online.follow/refresh
  [(re-frame/path [:route :params :id])]
  (fn follow-refresh
    [{id :db}]
    {:dispatch [:sd.online.tournament/load {:link (str "/tournaments/" id)
                                            :confirm? false}]}))


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


(defn tournaments-raw-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? :sd.online/tournaments %)]}
  (get-in db [:online :tournaments] {}))

(re-frame/reg-sub
  :sd.online.tournaments/raw
  tournaments-raw-sub)


(defn tournaments-filter-sub
  [[{:keys [list] :as input} f]]
  {:pre [(debug/spec-valid? (spec/nilable :sd.online/tournaments) input)
         (debug/spec-valid? :sd.filter/value f)]
   :post [(debug/spec-valid? :sd.online/tournaments-sub %)]}
  (assoc input
         :list (online/tournaments-filter-with (filter/->pattern f) list)
         :filter f))

(re-frame/reg-sub
  :sd.online.tournaments/filter
  :<- [:sd.online.tournaments/raw]
  :<- [:sd.filters/filter :online-tournaments]
  tournaments-filter-sub)


(defn tournaments-sort-sub
  [[{:keys [list] :as input} s]]
  {:pre [(debug/spec-valid? :sd.online/tournaments-sub input)
         (debug/spec-valid? :sd.sort/sort s)]
   :post [(debug/spec-valid? :sd.online/tournaments-sub %)]}
  (assoc input
         :list (online/tournaments-sort-with s list)
         :sort s))

(re-frame/reg-sub
  :sd.online.tournaments/sort
  :<- [:sd.online.tournaments/filter]
  :<- [:sd.sorts/sort :online-tournaments {:by :date}]
  tournaments-sort-sub)


(defn tournaments-sub
  [{:keys [status]
    :or {status :init}
    :as input}]
  {:pre [(debug/spec-valid? :sd.online/tournaments-sub input)]
   :post [(debug/spec-valid? :sd.online/tournaments-sub %)]}
  (when (= :init status)
    (re-frame/dispatch [:sd.online.tournaments/load]))
  input)

(re-frame/reg-sub
  :sd.online/tournaments
  :<- [:sd.online.tournaments/sort]
  tournaments-sub)


(defn tournament-id-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? (spec/nilable :sd.online.tournament/_id) %)]}
  (get-in db [:tournament :online :_id]))

(re-frame/reg-sub
  :sd.online.tournament/id
  tournament-id-sub)


(defn tournament-status-sub
  [[id user-status]]
  {:pre [(debug/spec-valid? (spec/nilable :sd.online.tournament/_id) id)]
   :post [(debug/spec-valid? :sd.online.tournament/status-sub %)]}
  (if (and (some? id) (= :logged user-status))
    :online
    :offline))

(re-frame/reg-sub
  :sd.online.tournament/status
  :<- [:sd.online.tournament/id]
  :<- [:sd.online.user/status]
  tournament-status-sub)


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


(defn follow-show-sub
  [db]
  {:pre [(debug/spec-valid? :sd.db/db db)]
   :post [(debug/spec-valid? :sd.online.follow/show? %)]}
  (get-in db [:online :follow :show?] false))

(re-frame/reg-sub
  :sd.online.follow/show?
  follow-show-sub)


(defn follow-status-sub
  [[show? {:keys [_id name] :as tournament-online}]]
  {:pre [(debug/spec-valid? :sd.online.follow/show? show?)
         (debug/spec-valid? (spec/nilable :sd.tournament/online) tournament-online)]
   :post [(debug/spec-valid? :sd.online.follow/status-sub %)]}
  (let [url (str (.-origin js/location) "/#/follow/" _id)]
    ;; (debug/log "follow-status" follow-show? tournament-status tournament-online url show?)
    {:show? show?
     :name name
     :url url}))

(re-frame/reg-sub
  :sd.online.follow/status
  :<- [:sd.online.follow/show?]
  :<- [:sd.tournament/online]
  follow-status-sub)


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
