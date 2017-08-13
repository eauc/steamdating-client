(ns steamdating.styles.5-components.sort-header
  (:require [garden.def :as gdef]))


(gdef/defstyles sort-header
  [:&-SortHeader {:cursor "pointer"
                   :white-space "nowrap"}
   [:&-icon {:opacity 0}
    [:&-show {:opacity 1}]]])
