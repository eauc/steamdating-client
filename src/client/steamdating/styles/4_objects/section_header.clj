(ns steamdating.styles.4-objects.section-header
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles section-header
  [:&-section-header {:border-bottom (:border box-model)
                      :padding-bottom (:padding box-model)
                      :text-align :center}
   (at-break
     :tablet
     [:& {:text-align :left}])])
