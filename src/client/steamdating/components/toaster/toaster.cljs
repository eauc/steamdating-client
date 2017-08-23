(ns steamdating.components.toaster.toaster
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.services.toaster]))


(defn toaster
  []
  (let [{:keys [type message]} @(re-frame/subscribe [:toaster])
        type-class (some->> type (name) (str "sd-Toaster-"))]
    [:div.sd-Toaster
     {:class (s/join " " [type-class (when type "sd-Toaster-show")])}
     message]))
