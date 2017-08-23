(ns steamdating.styles.4-objects.menu
  (:require [garden.def :as gdef]
            [garden.units :as gunits]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))

(def toggle-size
  (gunits/em 2.5))

(gdef/defstyles menu
  [:&-PageMenu {:z-index 1000
                :pointer-events "none"}
   [:&-insider {:background-color "white"
                :border (:border box-model)
                :border-color (:border colors)
                :box-sizing "border-box"
                :height "100%"
                :opacity 0
                :pointer-events "none"
                :text-align "left"}]
   (button [:&Toggle])
   [:&Toggle
    {:position "fixed"
     :bottom "10px"
     :right 0
     :height toggle-size
     :width toggle-size
     :box-shadow (:box_shadow box-model)
     :border-radius (gunits/em-div toggle-size 2)
     :pointer-events "all"}
    [:&-hide {:opacity 0}]]
   [:&Toggle
    {:border-top-right-radius 0
     :border-bottom-right-radius 0}]
   [:&Item {:display "block"
            :padding "0.75em 1.5em"
            :text-decoration "none"
            :color (:text colors)
            :cursor "pointer"}
    [:&:hover
     :&:active
     :&.active {:background-color (:hover colors)}]]
   [:&-show
    [:.sd-PageMenu-insider {:opacity 1
                            :pointer-events "all"}]]
   [:hr {:margin "0 1em 0 1em"}]
   (at-break
     :tablet
     [:&-insider {:background-color "transparent"
                  :border 0
                  :display "flex"
                  :flex-direction "column"
                  :flex-wrap "wrap"
                  :justify-content "start"
                  :opacity 1
                  :padding "5px 1em 0 0"
                  :pointer-events "all"
                  :text-align "right"}]
     [:&Toggle {:display "none"}]
     [:&Item {:padding-left "3em"}]
     [:hr {:width "100%"
           :border-width "1px"
           :margin 0}])])
