(ns steamdating.styles.5-components.ranking-bests
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]))


(gdef/defstyles ranking-bests
  [:&-ranking-bests {:align-items :flex-start
                     :display :flex
                     :flex-direction :column
                     :justify-content :space-between
                     :margin-top (:padding-large box-model)}

   (at-break
     :tablet
     [:& {:flex-direction :row}
      [:&-in-faction {:max-width "48%"}]
      [:&-scores {:max-width "48%"}]])])
