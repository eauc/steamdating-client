(ns steamdating.components.tournament.save-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.file.save-button :refer [file-save-button]]))


(defn tournament-save-button
  []
  (let [tournament @(re-frame/subscribe [:sd.tournament/tournament])]
    [file-save-button {:data tournament}]))
