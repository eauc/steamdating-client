(ns steamdating.services.online
  (:require [ajax.core :as ajax]
            [clairvoyant.core :refer-macros [trace-forms]]
            [cljs.spec.alpha :as spec]
            [cljsjs.auth0-lock]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.models.form :as form]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(trace-forms
  {:tracer (tracer :color "green")}


  (def domain
    "eauc.eu.auth0.com")


  (def client-id
    "vBhy4C4dLSkuoTbmldLYNxULGTyz6swK")


  (defn get-lock
    []
    (doto
        (js/Auth0Lock. client-id domain
                       (clj->js
                         {:ui {:autoClose true}
                          :auth {:loginAfterSignup true
                                 :redirect false
                                 :params {:scope "openid email permissions"}}}))
        (.on "authenticated" #(re-frame/dispatch
                                [:steamdating.online/login-success
                                 (aget % "idToken")]))))


  (defonce lock (atom nil))


  (re-frame/reg-fx
    :steamdating.online/show-lock
    (fn show-lock
      []
      (when (nil? @lock)
        (reset! lock (get-lock)))
      (.show @lock)))


  (db/reg-event-fx
    :steamdating.online/login
    (fn login
      []
      {:steamdating.online/show-lock nil}))


  (db/reg-event-fx
    :steamdating.online/logout
    [(re-frame/path :online)]
    (fn logout
      [{:keys [db]} _]
      {:db (dissoc db :token :tournaments)}))


  (db/reg-event-fx
    :steamdating.online/login-success
    [(re-frame/path :online)]
    (fn login-success
      [{:keys [db]} [token]]
      {:db (assoc db :token token)}))


  (db/reg-event-fx
    :steamdating.online/load-tournament
    [(re-frame/path :online)]
    (fn load-tournament
      [{:keys [db]} [link]]
      {:http-xhrio {:method :get
                    :uri (str "https://steamdating-data.herokuapp.com" link)
                    :headers {"Authorization" (str "Bearer " (:token db))}
                    :response-format (ajax/json-response-format {:keywords? true})
                    :on-success [:steamdating.online/load-tournament-success]
                    :on-failure [:steamdating.toaster/set
                                 {:type :error
                                  :message "Failed to load online tournament"}]}}))


  (db/reg-event-fx
    :steamdating.online/load-tournament-success
    [(re-frame/path :tournament)]
    (fn load-tournament-success
      [{:keys [db]} [{:keys [tournament] :as info}]]
      (let [new-tournament (assoc tournament :online (dissoc info :tournament))
            valid? (spec/valid? :steamdating.tournament/tournament new-tournament)]
        (if valid?
          {:db new-tournament}
          {:dispatch [:steamdating.toaster/set
                      {:type :error
                       :message "Failed to load online tournament"}]}))))


  (db/reg-event-fx
    :steamdating.online/load-tournaments
    [(re-frame/path :online)]
    (fn load-tournaments
      [{:keys [db]} _]
      {:http-xhrio {:method :get
                    :uri "https://steamdating-data.herokuapp.com/tournaments/mine"
                    :headers {"Authorization" (str "Bearer " (:token db))}
                    :response-format (ajax/json-response-format {:keywords? true})
                    :on-success [:steamdating.online/load-tournaments-success]
                    :on-failure [:steamdating.online/load-tournaments-error]}}))


  (db/reg-event-fx
    :steamdating.online/load-tournaments-success
    [(re-frame/path :online :tournaments)]
    (fn load-tournaments-success
      [{:keys [db]} [{:keys [tournaments]}]]
      (debug/spy "tournaments" {:tournaments tournaments})
      {:db tournaments}))


  (db/reg-event-fx
    :steamdating.online/load-tournaments-error
    [(re-frame/path :online)]
    (fn load-tournaments-success
      [{:keys [db]} _]
      {:db (assoc db :tournaments :failed)
       :dispatch [:steamdating.toaster/set
                  {:type :error
                   :message "Failed to load online tournaments"}]}))


  (db/reg-event-fx
    :steamdating.online/upload-current
    (fn upload-current
      [{:keys [db]} _]
      (let [token (get-in db [:online :token])
            link (get-in db [:tournament :online :link])
            update? (some? link)
            tournament (get db :tournament)
            data (-> (get db :online)
                     (select-keys [:name :date])
                     (merge (get-in db [:forms :online :edit]))
                     (assoc :tournament (dissoc tournament :online)))]
        {:http-xhrio {:method (if update? :put :post)
                      :uri (if update?
                             (str "https://steamdating-data.herokuapp.com" link)
                             "https://steamdating-data.herokuapp.com/tournaments/mine")
                      :headers {"Authorization" (str "Bearer " token)}
                      :format (ajax/json-request-format)
                      :params data
                      :response-format (ajax/json-response-format {:keywords? true})
                      :on-success [:steamdating.online/upload-current-success]
                      :on-failure [:steamdating.toaster/set
                                   {:type :error
                                    :message "Failed to upload current tournament"}]}})))


  (db/reg-event-fx
    :steamdating.online/clear-current-edit
    [(re-frame/path :forms)]
    (fn clear-current-edit
      [{:keys [db]} _]
      {:db (dissoc db :online)}))


  (db/reg-event-fx
    :steamdating.online/upload-current-success
    [(re-frame/path :tournament :online)]
    (fn upload-current-success
      [_ [info]]
      {:db info
       :dispatch-n [[:steamdating.online/clear-current-edit]
                    [:steamdating.online/load-tournaments]]}))


  (re-frame/reg-sub
    :steamdating.online/online
    (fn online-sub
      [db]
      (get db :online)))


  (re-frame/reg-sub
    :steamdating.online/status
    :<- [:steamdating.online/online]
    :<- [:steamdating.tournament/online]
    (fn status-sub
      [[{:keys [token]} {:keys [link]}]]
      (case [(some? token) (some? link)]
        [true true] :synced
        [true false] :online
        [false false] :offline
        [false true] :offline)))


  (re-frame/reg-sub
    :steamdating.online/tournaments
    :<- [:steamdating.online/online]
    (fn tournaments-sub
      [{:keys [token tournaments]}]
      (if (some? tournaments)
        (if (= :failed tournaments)
          {:status :failed
           :tournaments []}
          {:status :loaded
           :tournaments (reverse (sort-by :date tournaments))})
        (if (some? token)
          (do
            (re-frame/dispatch [:steamdating.online/load-tournaments])
            {:status :loading
             :tournaments []})
          {:status :offline
           :tournaments []}))))


  (re-frame/reg-sub
    :steamdating.online/edit-current
    (fn edit-current-sub
      [{:keys [forms tournament]}]
      (let [current-online (get tournament :online {})
            form-state {:base current-online
                        :edit (if (contains? forms :online)
                                (get-in forms [:online :edit])
                                current-online)}]
        (form/validate form-state :steamdating.online/edit))))

  )