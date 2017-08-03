(ns steamdating.styles.5-components.toaster
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(def opacity
  0.75)


(gdef/defstyles toaster
  [:&-Toaster {:opacity 0
               :pointer-events "none"
               :position "absolute"
               :left 0
               :right 0
               :bottom 0
               :padding (:padding box-model)
               :text-align "center"
               :color (:text-inverted colors)
               :font-weight "bold"}

   [:&-success {:background-color (assoc (:accent colors) :alpha opacity)}]
   [:&-warning {:background-color (assoc (:warning colors) :alpha opacity)}]
   [:&-error {:background-color (assoc (:error colors) :alpha opacity)}]
   [:&-info {:background-color (assoc (:primary colors) :alpha opacity)}]
   [:&-show {:opacity 1}]])
