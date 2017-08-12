(ns steamdating.styles.4-objects.file-open
  (:require [garden.def :as gdef]
            [steamdating.styles.1-tools.button :refer [button]]))


(gdef/defstyles file-open
  [:&-FileOpenButton {:position "relative"
                      :padding "0px"
                      :border 0
                      :overflow "hidden"}
   [:&-input {:width "0.1px"
              :height "0.1px"
              :opacity 0
              :overflow "hidden"
              :position "absolute"
              :z-index -1}]
   (button [:&-button])])
