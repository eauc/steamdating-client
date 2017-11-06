(ns steamdating.styles.5-components.online-follow
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles online-follow
  [:&-online-follow {:display :none
                     :flex-direction :column
                     :max-width "90%"
                     :padding (:padding box-model)
                     :background-color :white
                     :border (:border box-model)
                     :text-align :center}
   [:&.show {:display :flex}]


   [:&-link {:word-wrap :break-word}]


   [:&-qrcode {:flex-grow 1}]])
