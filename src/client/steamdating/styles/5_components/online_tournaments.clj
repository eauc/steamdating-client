(ns steamdating.styles.5-components.online-tournaments
  (:require [garden.color :as gcolor]
            [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles online-tournaments
  [:&-online-tournaments


   [:&-hint {:color (:text-muted colors)
             :font-style :italic}]


   [:&-error {:background-color (:error-bckgnd colors)
              :border-radius (:border-radius box-model)
              :color (:text-inverted colors)
              :display :flex
              :flex-direction :column
              :align-items :center
              :padding (:padding-large box-model)}

    [:.sd-button {:background-color (:error-bckgnd colors)
                  :border-color (:text-inverted colors)
                  :border-radius (:border-radius box-model)
                  :color (:text-inverted colors)}
     [:&:hover
      :&:focus {:background-color (gcolor/darken (:error-bckgnd colors) 12)
                :outline :none}]]]


   [:&-updated-at {:color (:text-muted colors)
                   :display :none
                   :font-size "0.8em"
                   :font-style :italic}]


   [:.sd-table

    [:&-caption {:align-items :stretch
                 :flex-direction :column}
     [:&-label {:text-align :left}]

     (at-break
       :tablet
       [:& {:align-items :center
            :flex-direction :row}])]

    [:tbody {:cursor :pointer}]

    [:td {:padding-bottom (:padding box-model)
          :padding-top (:padding box-model)
          :vertical-align :middle}]]])
