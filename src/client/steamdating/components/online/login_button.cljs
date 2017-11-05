(ns steamdating.components.online.login-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.services.online]))


(defn online-login-button
  []
  (let [state @(re-frame/subscribe [:sd.online.user/status])]
    [button
     (case state
       :not-logged {:icon "log-in"
                    :label "Log in"
                    :on-click #(re-frame/dispatch [:sd.online/login])}
       :logged {:icon "log-out"
                :label "Log out"
                :on-click #(re-frame/dispatch [:sd.online/logout])})]))
