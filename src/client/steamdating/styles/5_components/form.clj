(ns steamdating.styles.5-components.form
  (:require [garden.color :as gcolor]
            [garden.def :as gdef]
            [garden.units :as gunits]
            [steamdating.styles.0-settings.break :refer [break at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles form
  [:&-form


   [:.group {:margin-bottom "30vh"
             :margin-top (:padding box-model)
             :max-width (:pc break)
             :padding (:padding box-model)}
    (at-break
      :pc
      [:& {:padding (:padding-large box-model)}])]


   [:.legend {:background-color "white"
              :color (:text-light colors)
              :font-size "1.25em"
              :font-style "italic"
              :padding "0 0.25em 0 0"}]


   [:.form-info.error {:color (:error colors)
                       :font-size "0.85em"
                       :font-style "italic"
                       :margin-bottom (:padding-large box-model)
                       :margin-left (:padding box-model)
                       :margin-top "2em"}]


   [:.submit {:display :none}]])
