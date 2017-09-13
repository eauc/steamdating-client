(ns steamdating.styles.5-components.ranking-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles ranking-list
  [:&-Ranking
   (table [:&List])
   [:&List
    [:&-best {:background-color (:info-bckgnd colors)}]
    [:&-faction {:display :none}
     (at-break
       :tablet
       [:& {:display :inline}])]
    [:&-player {:cursor :pointer}]]])
