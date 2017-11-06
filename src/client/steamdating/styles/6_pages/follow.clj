(ns steamdating.styles.6-pages.follow
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles page-follow
  [:&-page-follow

   [:.sd-player-list {:margin-bottom "3em"}]

   [:.sd-table {:margin-top (:padding-large box-model)}]])
