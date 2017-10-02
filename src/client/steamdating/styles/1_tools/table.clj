(ns steamdating.styles.1-tools.table
  (:require [garden.color :as gcol]
            [garden.compiler :as gcomp]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(def border-color
  (gcol/lighten (:border colors) 80))

(defn table
  ([selectors {:keys [hover vertical-align]
               :or {hover (:hover colors)
                    vertical-align "top"}
               :as styles}]
   (vec
     (concat
       selectors
       [{:width "100%"
         :text-align "left"
         :border-collapse "collapse"}
        [:caption {:display :none}]
        [:tbody
         [:tr {:vertical-align vertical-align}
          [:&:hover {:background-color hover}]]]
        [:thead>:th {:border-color (:border colors)}]
        [:th :td {:padding "0.65em 0.25em"
                  :border-bottom (str "1px solid " (gcomp/render-css border-color))}]])))
  ([selectors]
   (table selectors {})))
