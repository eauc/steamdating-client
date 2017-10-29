(ns steamdating.styles.4-objects.table
  (:require [garden.def :as gdef]
            [garden.color :as gcol]
            [garden.compiler :as gcomp]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(def border-color
  (gcol/lighten (:border colors) 80))


(gdef/defstyles table
  [:&-table {:border-collapse "collapse"
             :text-align "left"
             :width "100%"}


   [:&-caption {:align-items :center
                :display :flex
                :flex-direction :row}

    [:&-label {:font-weight :bold
               :padding (:padding box-model)}]

    [:.sd-input {:flex-grow 1
                 :padding (:padding box-model)
                 :margin 0}]]


   [:tbody
    [:tr {:vertical-align :top}
     [:&:hover {:background-color (:hover colors)}]]]


   [:thead
    [:th {:border-color (:border colors)}]]


   [:th :td {:border-bottom (str "1px solid " (gcomp/render-css border-color))}]
   [:th {:padding (:padding box-model)}]
   [:td {:padding (:padding-small box-model)}]])
