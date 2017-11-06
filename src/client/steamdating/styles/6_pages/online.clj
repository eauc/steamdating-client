(ns steamdating.styles.6-pages.online
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles page-online
  [:&-page-online {:display :flex
                   :flex-direction :column
                   :flex-grow 1
                   :padding-bottom (:padding box-model)
                   :padding-top (:padding box-model)}


   [:fieldset {:margin 0
               :margin-bottom (:padding-large box-model)}]


   [:&-login {:align-items :center
              :display :flex
              :flex-direction :column
              :flex-grow 1
              :justify-content :space-around}]])
