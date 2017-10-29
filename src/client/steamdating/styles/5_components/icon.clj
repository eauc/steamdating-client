(ns steamdating.styles.5-components.icon
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]))


(gdef/defstyles icon
  [:&-icon {:fill :none
            :height "1.2em"
            :stroke "currentcolor"
            :stroke-width 2
            :stroke-linecap "round"
            :stroke-linejoin "round"
            :vertical-align "sub"
            :width "1.2em"}]


  [:&-faction-icon {:align-items :center
                    :display :flex
                    :flex-direction :row
                    :flex-wrap :nowrap}
   [:img {:height "2em"
          :margin "-0.2em 0"
          :width "2em"}]
   (at-break
     :tablet
     [:&-label {:margin-left (:padding box-model)}])])
