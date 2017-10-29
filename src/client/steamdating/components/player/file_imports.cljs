(ns steamdating.components.player.file_imports
  (:require [steamdating.components.file.open-button :refer [file-open-button]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.players]))


(defn player-file-imports
  []
  [:div.sd-player-file-imports
   [:h3.sd-section-header "Import Players"]
   [:div.sd-player-file-imports-actions
    [file-open-button
     {:icon "file-plus"
      :id "import-t3-csv"
      :label "T3 CSV"
      :on-open [:sd.players.import/t3]}]
    [file-open-button
     {:icon "file-plus"
      :id "import-cc-json"
      :label "CC JSON"
      :on-open [:sd.players.import/cc]}]]])
