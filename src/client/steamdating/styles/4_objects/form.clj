(ns steamdating.styles.4-objects.form
  (:require [garden.color :as gcolor]
            [garden.def :as gdef]
            [garden.units :as gunits]
            [steamdating.styles.0-settings.break :refer [break at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))


(def submit-size
  (gunits/em 2.5))


(def submit-color
  (gcolor/hex->rgb "#B0B0B0"))


(def group-horizontal-padding
  "0.5em")


(def group-horizontal-padding-large
  "2em")


(gdef/defstyles form
  [:&-Form {:margin-bottom submit-size}
   [:&-group {:position "relative"
              :max-width (:pc break)
              :padding-top "1em"
              :padding-right group-horizontal-padding
              :padding-bottom (gunits/em-div submit-size 2)
              :padding-left group-horizontal-padding}]
   [:&-legend {:padding "0 0.25em 0 0"
               :color (:text-light colors)
               :background-color "white"
               :font-size "1.25em"
               :font-style "italic"}]
   (button [:&-submit] {:background-color (:accent colors)
                        :color (:text-inverted colors)
                        :border-radius (gunits/em-div submit-size 2)
                        :position "absolute"
                        :bottom 0
                        :left group-horizontal-padding
                        :margin-bottom (gunits/em- 0 (gunits/em-div submit-size 2))
                        :height submit-size})
   [:&-disabled
    :&-disabled:hover
    :&-disabled:focus {:pointer-events "none"
                       :background-color submit-color
                       :border-color (gcolor/darken submit-color 20)}]
   [:&-error
    [:&-info {:margin "0.5em 0 1em 0"
              :color (:error colors)
              :font-size "0.85em"
              :font-style "italic"}]]
   (at-break
     :tablet
     [:&-group {:padding-top group-horizontal-padding-large
                :padding-right group-horizontal-padding-large
                :padding-bottom (gunits/em+ 1 (gunits/em-div submit-size 2))
                :padding-left group-horizontal-padding-large}]
     [:&-submit {:left group-horizontal-padding-large}])])
