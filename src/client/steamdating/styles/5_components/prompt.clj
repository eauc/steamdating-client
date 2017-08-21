(ns steamdating.styles.5-components.prompt
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))

(def control
  {:margin "0 0.5em"
   :flex-grow 1})

(gdef/defstyles prompt
  [:&-Prompt {:opacity 0
              :width "100%"
              :height "100%"}
   [:&-mask {:display "flex"
             :flex-direction "column"
             :justify-content "space-around"
             :align-items "center"
             :position "fixed"
             :width "100%"
             :height "100%"
             :background-color "rgba(0,0,0,0.5)"}]
   [:&-content {:display "flex"
                :flex-direction "column"
                :max-width "70%"
                :padding (:padding box-model)
                :background-color "white"
                :border (:border box-model)
                :text-align "center"}]
   [:&-msg {:padding (:padding box-model)
            :margin-top "1em"
            :min-width "10em"
            :min-height "2em"}]
   [:&-controls {:display "flex"
                 :flex-direction "row"
                 :justify-content "space-around"
                 :padding (:padding box-model)}]
   (button
     [:&-control-ok]
     (merge control
            {:background-color (:accent colors)
             :color (:text-inverted colors)}))
   (button
     [:&-control-cancel]
     control)
   [:&-show {:opacity 1
             :pointer-events "all"}]])
