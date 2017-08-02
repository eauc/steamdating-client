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
  [:&-PageMenu {:text-align "center"
                :position "relative"}
   (button
     [:&Toggle]
     {:border-radius (gunits/em-div toggle-size 2)
      :z-index 1000
      :position "absolute"
      :bottom "10px"
      :right "15px"
      :height toggle-size
      :width toggle-size
      :box-shadow (:box_shadow box-model)})
   [:&Item {:display "none"
            :padding "0.75em 0.5em"
            :text-decoration "none"
            :color (:text colors)
            :cursor "pointer"}
    [:&:hover
     :&:active
     :&.active {:background-color (:hover colors)}]]
   [:&-show {:border-top (:border box-model)
             :border-color (:border colors)}
    [:.sd-PageMenuItem {:display "block"}]
    [:.sd-PageMenuToggle {:top 0
                          :margin-top (gunits/em- 0 (gunits/em-div toggle-size 2))}]]
   [:hr {:margin "0 1em 0 1em"}]
   (at-break
     :tablet
     [:& {:display "flex"
          :flex-direction "row"
          :flex-wrap "wrap"
          :justify-content "space-around"}
      [:&-show {:border-top "0px"}]]
     [:&Toggle {:display "none"}]
     [:&Item {:display "block"
              :flex-grow 1}]
     [:hr {:width "100%"
           :border-width 0
           :margin 0}])
   (at-break
     :pc
     {:padding "0 1em 0 0"
      :text-align "right"}
     [:& {:display "initial"}]
     [:&Item {:padding-left "3em"}]
     [:hr {:border-width "1px"
           :margin-bottom 0
           :margin-top 0}])])
