(ns steamdating.services.online
  (:require [cljsjs.auth0-lock]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]))


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
    {:db (dissoc db :token)}))


(db/reg-event-fx
  :steamdating.online/login-success
  [(re-frame/path :online)]
  (fn login-success
    [{:keys [db]} [token]]
    {:db (assoc db :token token)}))


(re-frame/reg-sub
  :steamdating.online/online
  (fn status-sub
    [db]
    (get db :online)))


(re-frame/reg-sub
  :steamdating.online/status
  :<- [:steamdating.online/online]
  (fn status-sub
    [{:keys [token]}]
    (if (some? token)
      :online
      :offline)))
