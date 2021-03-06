(ns steamdating.styles.5-components.page
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles page
  [:&-page {:display :flex
           :flex-direction :column
           :position :relative}


   [:&-container {:align-items :stretch
                  :display :flex
                  :flex-direction :row
                  :flex-grow 1
                  :overflow :auto}]


   [:&-content {:box-sizing :border-box
                :display :flex
                :flex-direction :column
                :flex-grow 1
                :max-width "100%"
                :padding (:padding box-model)}
    (at-break
      :pc
      [:& {:padding (:padding-large box-model)}])]


   [:&-menu {:background-color "white"
             :box-shadow (:box-shadow box-model)
             :box-sizing :border-box
             :display :flex
             :flex-direction :column
             :height "100%"
             :min-width "40vw"
             :max-width "80vw"
             :padding-top (:padding box-model)
             :position :absolute
             :right 0
             :transition "right 0.25s"
             :top 0
             :z-index 500}


    [:&-toggle {:align-items "center"
                :background-color "white"
                :bottom "43%"
                :border-bottom-left-radius "1em"
                :border-color "#CCC"
                :border-right-color :white
                :border-style "solid"
                :border-top-left-radius "1em"
                :border-width "1px 0 1px 1px"
                :display :flex
                :font-size "1.4em"
                :justify-content "space-around"
                :position :fixed
                :height "2em"
                :right 0
                :transition "right 0.25s"
                :width "2em"}
     (at-break
       :tablet
       [:& {:display :none}])]


    [:&-item {:background-color :transparent
              :border 0
              :cursor :pointer
              :padding (:padding box-model)
              :padding-bottom (:padding-large box-model)
              :padding-top (:padding-large box-model)
              :white-space :nowrap
              :text-align :left}
     [:&.disabled {:color (:text-muted colors)
                   :font-style :italic
                   :pointer-events :none}]
     [:&.active
      :&:hover
      :&:focus {:background-color (:hover colors)
                :outline :none}]

     [:&-label {:padding-right (:padding box-model)}]]


    [:hr {:border (:border box-model)
          :margin 0
          :width "100%"}]

    (at-break
      :tablet
      [:& {:box-shadow :none
           :flex-shrink 0
           :min-width 0
           :position :initial}
       [:&-item {:text-align :right}]])


    (at-break
      :pc
      [:& {:padding (:padding-large box-model)}
       [:&-item {:padding (:padding-large box-model)}]])]])
