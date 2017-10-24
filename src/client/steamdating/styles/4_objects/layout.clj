(ns steamdating.styles.4-objects.layout
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles layout
  [:& {:display :flex
       :flex-direction :column
       :height "100vh"}
   [:.page {:display :flex
            :flex-direction :column
            :flex-grow 1
            :justify-content :space-around}
    [:.loading {:text-align :center}]]])
