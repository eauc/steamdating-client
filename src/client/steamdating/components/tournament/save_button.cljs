(ns steamdating.components.tournament.save-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.file-save :refer [file-save]]
            [reagent.core :as reagent]))


(defn save-button
  []
  (let [tournament @(re-frame/subscribe [:steamdating.tournament/tournament])]
    [file-save tournament]))
