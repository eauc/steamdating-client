(ns steamdating.styles.5-components.file-open-button
  (:require [garden.def :as gdef]))


(gdef/defstyles file-open-button
  [:&-file-open-button {:border 0
                        :display :flex
                        :flex-direction :row
                        :overflow :hidden
                        :padding 0
                        :position :relative}


   [:.input {:height "0.1px"
             :left 0
             :opacity 0
             :overflow :hidden
             :position :absolute
             :top 0
             :width "0.1px"
             :z-index -1}]


   [:.sd-button {:flex-grow 1}]])
