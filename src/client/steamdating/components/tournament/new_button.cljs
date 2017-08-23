(ns steamdating.components.tournament.new-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]))


(defn new-button
  []
  [:button.sd-TournamentNewButton
   {:on-click #(re-frame/dispatch
                 [:steamdating.tournament/confirm-set {}])}
   [icon "file-o"]
   [:span " New"]])
