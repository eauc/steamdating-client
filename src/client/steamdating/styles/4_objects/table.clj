(ns steamdating.styles.4-objects.table
  (:require [garden.def :as gdef]
            [garden.color :as gcol]
            [garden.compiler :as gcomp]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(def border-color
  (gcol/lighten (:border colors) 80))


(gdef/defstyles table
  [:&-table {:border-collapse "collapse"
             :text-align "left"
             :width "100%"}


   [:caption {:margin-left (str "-" (:padding box-model))
              :max-width "100vw"
              :padding-left (:padding box-model)
              :padding-right (:padding box-model)}
    (at-break
      :tablet
      [:& {:padding-right 0}])]

   [:&-caption {:align-items :baseline
                :display :flex
                :flex-direction :row
                :margin-bottom (:padding box-model)}

    [:&-label {:flex-shrink 0
               :font-weight :bold
               :padding (:padding box-model)
               :padding-left 0
               :white-space :nowrap}]

    [:.sd-filter-input {:flex-grow 1}]]


   [:tbody
    [:tr {:vertical-align :top}
     [:&:hover {:background-color (:hover colors)}]]]


   [:thead
    [:th {:border-color (:border colors)}]]


   [:th :td {:border-bottom (str "1px solid " (gcomp/render-css border-color))}]
   [:th {:padding (:padding box-model)}]
   [:td {:padding (:padding-small box-model)}]])
