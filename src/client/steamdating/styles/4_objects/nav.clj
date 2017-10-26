(ns steamdating.styles.4-objects.nav
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles nav
  [:&
   [:.nav {:background-color (:primary colors)
           :box-shadow (:box-shadow box-model)
           :display :flex
           :flex-direction :row
           :flex-wrap :wrap
           :z-index 1000}
    [:.brand {:color (:text-inverted colors)
              :flex-grow 1
              :font-family :Cookie
              :font-size "1.8em"
              :order 1
              :padding "0.2em"}]
    [:.menu {:flex-shrink 0
             :order 3
             :width "100%"}
     [:.container {:height 0
                   :overflow "hidden"
                   :transition "height 0.25s"}]
     [:.content {:display :flex
                 :flex-direction :column}
      [:.item {:color (:text-inverted colors)
               :padding (:padding box-model)
               :padding-bottom "0.75em"
               :padding-top "0.75em"
               :text-decoration :none}
       [:&.active
        :&:hover {:background-color (:primary-dark colors)}]]]]
    [:.actions {:order 2}
     [:.toggle {:background-color :transparent
                :border 0
                :color (:text-inverted colors)
                :height "2em"
                :font-size "1.4em"
                :width "2em"}
      [:&:focus
       :&:hover {:background-color (:primary-dark colors)
                 :outline 0}]]]]])
