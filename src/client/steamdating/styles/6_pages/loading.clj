(ns steamdating.styles.6-pages.loading
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles page-loading
  [:&-page-loading {:align-items :center
                    :display :flex
                    :flex-direction :column
                    :flex-grow 1
                    :justify-content :space-around}])
