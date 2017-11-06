(ns steamdating.styles.5-components.prompt
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles prompt
  [:&-prompt {:background-color :white
              :border (:border box-model)
              :border-radius (:border-radius box-model)
              :box-shadow (:box-shadow box-model)
              :display :none
              :padding (:padding box-model)}


   [:&.show {:display :block}]

   [:&-message {:padding (:padding box-model)
                :text-align :center
                :white-space :pre-line}]

   [:&-controls {:display :flex
                 :flex-direction :row
                 :justify-content :space-around}

    [:button {:margin (:padding box-model)}]]])
