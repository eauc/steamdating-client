(ns steamdating.styles.4-objects.button
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles button
  [:&-button {:background-color :white
              :border (:border box-model)
              :border-radius (:border-radius box-model)
              :padding (:padding box-model)}
   [:&:hover
    :&:focus {:background-color (:hover colors)}]
   [:&.success {:background-color (:valid-bckgnd colors)
                :color (:text-inverted colors)}
    [:&:hover
     :&:focus {:background-color (:valid colors)}]]])
