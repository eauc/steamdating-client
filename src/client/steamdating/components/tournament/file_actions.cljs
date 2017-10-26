(ns steamdating.components.tournament.file-actions
  (:require [steamdating.components.tournament.new-button :refer [tournament-new-button]]
            [steamdating.components.tournament.open-button :refer [tournament-open-button]]
            [steamdating.components.tournament.save-button :refer [tournament-save-button]]))


(defn tournament-file-actions
  []
  [:div.sd-tournament-file-actions
   [:h3.sd-section-header "Files"]
   [:div.actions
    [tournament-new-button]
    [tournament-open-button]
    [tournament-save-button]]])
