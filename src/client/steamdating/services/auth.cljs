(ns steamdating.services.auth
  (:require [cljs.loader :as loader]
            [cljsjs.auth0-lock]))


(defn get-lock
  [{:keys [client-id domain on-authenticated]}]
  (doto
      (js/Auth0Lock.
        client-id domain
        (clj->js
          {:ui {:autoClose true}
           :auth {:loginAfterSignup true
                  :redirect false
                  :params {:scope "openid email permissions"}}}))
      (.on "authenticated" on-authenticated)))


(defonce lock (atom nil))


(defn show-login
  [params]
  (when (nil? @lock)
    (reset! lock (get-lock params)))
  (.show @lock))


(loader/set-loaded! :auth)
