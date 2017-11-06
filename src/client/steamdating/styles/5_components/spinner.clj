(ns steamdating.styles.5-components.spinner
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles spinner
  [:&-spinner {:align-items :center
               :background-color "rgba(0,0,0,0.6)"
               :border-radius "5px"
               :box-shadow "0px 0px 5px 5px rgba(0,0,0,0.6)"
               :color (:text-inverted colors)
               :display :none
               :flex-direction :column
               :padding (:padding-large box-model)}
   [:&.show {:display :flex}]

   [:&-icon {:margin (:padding-large box-model)}]])
