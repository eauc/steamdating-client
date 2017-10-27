(ns steamdating.styles.5-components.prompt
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles prompt
  [:&-prompt {:align-items :center
             :background-color "rgba(0,0,0,0.3)"
             :bottom 0
             :display :flex
             :flex-direction :column
             :justify-content :space-around
             :left 0
             :opacity 0
             :pointer-events :none
             :position :fixed
             :right 0
             :top 0
             :z-index 2000}
   [:&.show {:opacity 1
             :pointer-events :initial
             :transition "opacity 0.25s"}]

   [:&-content {:background-color :white
               :border (:border box-model)
               :border-radius (:border-radius box-model)
               :box-shadow (:box-shadow box-model)
                :padding (:padding box-model)}]

   [:&-message {:padding (:padding box-model)
                :text-align :center
                :white-space :pre-line}]

   [:&-controls {:display :flex
                 :flex-direction :row
                 :justify-content :space-around}

    [:button {:margin (:padding box-model)}]]])
