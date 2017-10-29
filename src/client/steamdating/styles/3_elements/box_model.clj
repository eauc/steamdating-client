(ns steamdating.styles.3-elements.box-model
  (:require [garden.def :as gdef]))

(gdef/defstyles box-model
  [:* :html :body {:box-sizing :border-box
                   :min-height 0
                   :min-width 0}])
