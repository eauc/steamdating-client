(ns steamdating.styles.5-components.nav
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(defn action-button
  ([selectors props]
   (conj selectors
         (merge {:align-items :center
                 :background-color :transparent
                 :border 0
                 :color (:text-inverted colors)
                 :display :flex
                 :flex-direction :column
                 :height "2em"
                 :justify-content :center
                 :font-size "1.4em"
                 :width "2em"}
                props)
         [:&:focus
          :&:hover {:background-color (:primary-dark colors)
                    :outline 0}]))
  ([selectors]
   (action-button selectors {})))


(gdef/defstyles nav
  [:&
   [:&-nav {:background-color (:primary colors)
            :box-shadow (:box-shadow box-model)
            :display :flex
            :flex-direction :row
            :flex-wrap :wrap
            :z-index 1000}


    [:&-brand {:color (:text-inverted colors)
               :flex-grow 1
               :font-family :Cookie
               :font-size "1.8em"
               :order 1
               :padding "0.2em"}]


    [:&-menu {:flex-shrink 0
              :order 3
              :width "100%"}


     [:&-container {:height 0
                    :overflow "hidden"
                    :transition "height 0.25s"}]


     [:&-content {:display :flex
                  :flex-direction :column}]


     [:&-item {:color (:text-inverted colors)
               :padding (:padding box-model)
               :padding-bottom "0.75em"
               :padding-top "0.75em"
               :text-decoration :none}
      [:&.active
       :&:hover {:background-color (:primary-dark colors)}]]]


    [:&-actions {:order 2}


     [:&-content {:display :flex
                  :flex-direction :row
                  :align-item :stretch}


      (action-button [:.sd-button] {:align-items :center
                                    :border-radius 0
                                    :margin 0
                                    :padding 0})
      [:.sd-button
       [:&-label {:display :none}]]]


     (action-button [:&-toggle])]]

   (at-break
     :tablet
     [:&-nav {:flex-direction :row}

      [:&-brand {:flex-grow 0}]

      [:&-menu {:flex-grow 1
                :order 2
                :width :auto}
       [:&-container {:height "100% !important"}]
       [:&-content {:flex-direction :row
                    :height "100%"
                    :align-items :flex-end
                    :line-height "1.35em"}]]

      [:&-actions {:order 3}
       [:&-toggle {:display :none}]]])])
