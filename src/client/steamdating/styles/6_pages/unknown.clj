(ns steamdating.styles.6-pages.unknown
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles page-unknown
  [:&-page-unknown {:align-items :center
                    :display :flex
                    :flex-direction :column
                    :flex-grow 1
                    :justify-content :space-around}])
