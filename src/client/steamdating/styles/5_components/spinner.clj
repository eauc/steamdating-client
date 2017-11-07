(ns steamdating.styles.5-components.spinner
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles spinner
  [:&-spinner {:align-items :center
               :text-shadow "0px 0px 3px black"
               :color (:text-inverted colors)
               :display :none
               :flex-direction :column
               :padding (:padding-large box-model)}
   [:&.show {:display :flex}]

   [:&-icon {:margin (:padding-large box-model)}
    [:svg {:background-color "rgba(0,0,0,0.2)"
           :box-shadow "0 0 2px 2px rgba(0,0,0,0.2)"
           :border-radius "10px"}]]])
