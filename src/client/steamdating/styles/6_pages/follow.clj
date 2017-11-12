(ns steamdating.styles.6-pages.follow
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles page-follow
  [:&-page-follow

   [:&-filter {:position :relative}
    [:.sd-input-value {:height "2.5em"}]
    [:.sd-table-clear-filter {:top 0}]]

   [:.sd-player-list {:margin-bottom "3em"}]

   [:.sd-table {:margin-top (:padding-large box-model)}]])
