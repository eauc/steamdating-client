(ns steamdating.styles.5-components.ranking-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.table :refer [table]]
            [steamdating.styles.1-tools.button :refer [button]]))


(gdef/defstyles ranking-list
  [:&-Ranking
   (table [:&List])
   [:&List
    [:&-best {:background-color (:info-bckgnd colors)}]
    [:&-faction {:display :none}
     (at-break
       :tablet
       [:& {:display :inline}])]
    [:&-player {:cursor :pointer}]
    [:td {:vertical-align :middle}]
    [:td.sd-RankingList-drop-col {:padding "0.35em 0.2em"
                                  :white-space "nowrap"
                                  :width "1%"}
     (button [:button])]
    [:&-droped {:background-color (:disabled colors)}
     [:td.sd-RankingList-drop-col {:padding "0.2em"}]]
    [:&-undrop {:font-size "0.75em"}]]])
