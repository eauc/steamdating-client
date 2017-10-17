(ns steamdating.components.online.subscribe-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.online]))


(defn online-subscribe-button
  []
  (let [{:keys [can-subscribe? has-subscribed? name] :as state}
        @(re-frame/subscribe [:steamdating.online/push])]
    (if can-subscribe?
      [:div
       (when has-subscribed?
         [:p "You already subscribed to notifications."])
       [:button.sd-OnlineSubscribe
        {:on-click #(re-frame/dispatch [:steamdating.online.push/create-subscription])}
        [icon "message-circle"]
        " Subscribe to notifications"]]
      [:p.sd-text-muted
       "Your browser does not support notifications."])))
