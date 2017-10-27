(ns steamdating.styles.5-components.layout
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles layout
  [:& {:display :flex
       :flex-direction :column
       :height "100vh"
       :overflow :hidden}
   [:&-nav {:flex-grow 0
            :flex-shrink 0}]
   [:&-page {:flex-grow 1
             :flex-shrink 1}]])
