(ns steamdating.components.tournament.open-button
  (:require [steamdating.components.generics.file-open :as file]
            [steamdating.components.generics.icon :refer [icon]]))


(defn open-button
  []
  [file/open-button
   {:id "tournament-open"
    :on-open [:steamdating.tournament/open-file]}
   [:span
    [icon "file-text"]
    " Open"]])
