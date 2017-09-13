(ns steamdating.styles.5-components.ranking-bests
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles ranking-bests
  [:&-RankingBests {:display :flex
                    :flex-direction :column
                    :align-items :flex-start
                    :justify-content :space-between}
   (table [:&-factions])
   (table [:&-scores])
   (at-break
     :tablet
     [:& {:flex-direction :row}
      [:&-factions {:max-width "48%"}]
      [:&-scores {:max-width "48%"}]])])
