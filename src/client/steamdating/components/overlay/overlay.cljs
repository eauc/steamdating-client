(ns steamdating.components.overlay.overlay
  (:require [re-frame.core :as re-frame]
            [steamdating.components.online.follow :refer [online-follow]]
            [steamdating.components.overlay.prompt :refer [prompt]]
            [steamdating.components.overlay.spinner :refer [spinner]]
            [steamdating.services.ui]))


(defn overlay-render
  [{:keys [state]}]
  (let [{:keys [show?]} state]
    [:div.sd-overlay {:class (when show? "show")}
     [prompt]
     [online-follow]
     [spinner]]))


(defn overlay
  []
  (let [state @(re-frame/subscribe [:sd.ui/overlay])]
    [overlay-render
     {:state state}]))
