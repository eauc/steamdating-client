(ns steamdating.styles.4-objects.layout
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles layout
  [:& {:display :flex
       :flex-direction :column
       :height "100vh"}
   [:.nav {:flex-grow 0
           :flex-shrink 0}]
   [:.page {:flex-grow 1
            :flex-shrink 1}]])
