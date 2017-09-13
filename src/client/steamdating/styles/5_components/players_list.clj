(ns steamdating.styles.5-components.players-list
  (:require [garden.def :as gdef]
            [garden.selectors :as gsel]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles players-list
  [:&-PlayersList
   [:.sd-Input {:max-width "20em"}]
   (table [:&-list])
   [:&-list
    [:tr {:cursor "pointer"}
     [(gsel/th (gsel/nth-child "1n+4"))
      (gsel/td (gsel/nth-child "1n+4")) {:display "none"}
      (at-break
        :tablet
        [:& {:display "table-cell"}])]]]
   [:&Row
    [:&-faction {:white-space "nowrap"}]]]
  (table [:&-RankingList]))
