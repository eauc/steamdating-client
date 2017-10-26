(ns steamdating.components.tournament.new-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.models.tournament :refer [->tournament]]
            [steamdating.services.tournament]))


(defn tournament-new-button
  []
  [button {:icon "file"
           :label "New"
           :on-click #(re-frame/dispatch
                        [:sd.tournament/confirm-set (->tournament)])}])
