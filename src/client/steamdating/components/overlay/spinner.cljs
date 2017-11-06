(ns steamdating.components.overlay.spinner
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.spinner]))


(defn spinner-render
  [{:keys [state]}]
  (let [{:keys [message]} state]
    [:div.sd-spinner
     {:class (when (some? state) "show")}
     [:div.sd-spinner-icon [icon {:name "loader"}]]
     [:div.sd-spinner-message message]]))


(defn spinner
  []
  (let [state @(re-frame/subscribe [:sd.spinner/spinner])]
    [spinner-render
     {:state state}]))
