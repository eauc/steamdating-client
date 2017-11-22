(ns steamdating.styles.5-components.filter-input
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))



(gdef/defstyles filter-input
  [:&-filter-input {:display :flex}


   [:.sd-input {:flex-grow 1
                :margin-bottom 0}

    [:&-value {:height "2.5em"}]]


   [:&-clear {:border-radius 0
              :height "2.5em"
              :margin-left "-1px"
              :z-index 100}

    (at-break
      :tablet
      [:& {:right (:padding-large box-model)}])]])
