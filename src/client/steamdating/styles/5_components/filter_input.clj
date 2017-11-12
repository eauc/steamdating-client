(ns steamdating.styles.5-components.filter-input
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))



(gdef/defstyles filter-input
  [:&-filter-input {}


   [:.sd-input {}

    [:&-value {:height "2.5em"}]]


   [:&-clear {:border-radius 0
              :height "2.5em"
              :position :absolute
              :right 0
              :top 0
              :z-index 100}]])
