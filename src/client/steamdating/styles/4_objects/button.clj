(ns steamdating.styles.4-objects.button
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles button
  [:&-button {:align-items :flex-end
              :background-color :white
              :border (:border box-model)
              :border-radius (:border-radius box-model)
              :color (:text colors)
              :display :flex
              :flex-direction :row
              :justify-content :center
              :padding (str (:padding box-model) " " (:padding-large box-model))
              :text-decoration :none}
   [:&:hover
    :&:focus {:background-color (:hover colors)}]


   [:&.success {:background-color (:valid-bckgnd colors)
                :color (:text-inverted colors)}
    [:&:hover
     :&:focus {:background-color (:valid colors)}]]


   [:&-label {:margin "0 0.25em"}]])
