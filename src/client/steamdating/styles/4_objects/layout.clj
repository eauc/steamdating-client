(ns steamdating.styles.4-objects.layout
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.break :refer [at-break]]))

(gdef/defstyles layout
  [:& {:display "flex"
       :flex-direction "column"
       :height "100vh"}
   [:&-Page-container {:flex-grow 1
                       :position "relative"}]
   [:&-Page {:position "absolute"
             :height "100%"
             :width "100%"
             :display "flex"
             :flex-direction "column"}]
   [:&-PageMenu {:order 2}]
   [:&-PageContent {:order 1
                    :flex-grow 1
                    :position "relative"}
    [:&-insider {:position "absolute"
                 :box-sizing "border-box"
                 :width "100%"
                 :height "100%"
                 :overflow "auto"
                 :padding "3px"}]]
   [:&-Prompt-container {:z-index 2000
                         :position "absolute"
                         :top 0
                         :bottom 0
                         :left 0
                         :right 0
                         :background-color "transparent"
                         :pointer-events "none"}]
   (at-break
     :tablet
     [:&-PageContent
      [:&-insider {:padding "1em"}]])
   (at-break
     :pc
     [:&-Page {:flex-direction "row"}]
     [:&-PageMenu {:order 0}]
     [:&-PageContent {:order 0}])])
