(ns steamdating.components.online.online-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.online]))


(defn online-button
  []
  (case @(re-frame/subscribe [:steamdating.online/status])
    :offline [:button.sd-OnlineButton
              {:type :button
               :title "Log in"
               :on-click #(re-frame/dispatch [:steamdating.online/login])}
              [icon "log-in"]
              [:span.sd-OnlineButton-text
               " Log in"]]
    :online [:button.sd-OnlineButton
             {:type :button
              :title "Log out"
              :on-click #(re-frame/dispatch [:steamdating.online/logout])}
             [icon "log-out"]
             [:span.sd-OnlineButton-text
               " Log out"]]))
