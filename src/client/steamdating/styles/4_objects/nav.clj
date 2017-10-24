(ns steamdating.styles.4-objects.nav
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles nav
  [:&
   [:.nav {:background-color (:primary colors)
           :box-shadow (:box-shadow box-model)}
    [:.brand {:font-family :Cookie
              :font-size "1.8em"
              :color (:text-inverted colors)
              :padding "0.2em"}]]])
