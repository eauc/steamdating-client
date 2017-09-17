(ns steamdating.components.player.file-import
  (:require [steamdating.components.generics.file-open :as file-open]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.players]))


(defn file-import
  []
  [:div
   [:h3.sd-FilePage-header "Import Players"]
    [:div.sd-FilePage-fileActions
     [file-open/open-button
      {:id "import-t3-csv"
       :on-open [:steamdating.players/import-t3]}
      [:span
       {:title "Table Top Tournament CSV"}
       [icon "file-plus"]
       " T3 CSV"]]
     [file-open/open-button
      {:id "import-cc-json"
       :on-open [:steamdating.players/import-cc]}
      [:span
       {:title "Conflict Chamber JSON"}
       [icon "file-plus"]
       " CC JSON"]]]])
