(ns steamdating.components.toaster.toaster
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.services.toaster]))


(defn toaster
  []
  (let [{:keys [type message]} @(re-frame/subscribe [:sd.toaster/toaster])]
    [:div.sd-toaster-content {:class type}
     message]))
