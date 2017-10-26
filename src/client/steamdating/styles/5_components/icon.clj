(ns steamdating.styles.5-components.icon
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles icon
  [:&-icon {:fill :none
            :height "1.2em"
            :stroke "currentcolor"
            :stroke-width 2
            :stroke-linecap "round"
            :stroke-linejoin "round"
            :vertical-align "sub"
            :width "1.2em"}])
