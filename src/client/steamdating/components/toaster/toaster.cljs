(ns steamdating.components.toaster.toaster
  (:require [re-frame.core :as re-frame]
            [steamdating.components.toaster.handler]
            [steamdating.components.toaster.sub]
            [clojure.string :as str]))


(defn toaster
  []
  (let [state (re-frame/subscribe [:toaster])]
    (fn toaster-component
      []
      (let [{:keys [type message]} @state
            type-class (some->> type (name) (str "sd-Toaster-"))]
        [:div.sd-Toaster
         {:class (str/join " " [type-class (when type "sd-Toaster-show")])}
         message]))))
