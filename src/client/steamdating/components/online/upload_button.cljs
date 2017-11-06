(ns steamdating.components.online.upload-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.services.online]))


(defn online-upload-button
  []
  [button
   {:icon "upload-cloud"
    :label "Upload"
    :on-click #(re-frame/dispatch [:sd.online.tournament/upload])}])
