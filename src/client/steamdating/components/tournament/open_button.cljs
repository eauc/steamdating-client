(ns steamdating.components.tournament.open-button
  (:require [steamdating.components.file.open-button :refer [file-open-button]]))


(defn tournament-open-button
  []
  [file-open-button
   {:id "tournament-open"
    :on-open [:sd.tournament.file/open]}])
