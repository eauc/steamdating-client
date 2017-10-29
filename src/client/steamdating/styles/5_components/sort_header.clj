(ns steamdating.styles.5-components.sort-header
  (:require [garden.def :as gdef]))


(gdef/defstyles sort-header
  [:&-sort-header {:cursor :pointer
                   :user-select :none
                   :white-space :nowrap}


   [:.sd-icon {:opacity 0}]


   [:&.active
    [:.sd-icon {:opacity 1}]]])
