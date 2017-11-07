(ns steamdating.styles.5-components.game-edit
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles game-edit
  [:&-game-edit


   [:&-result {:cursor :pointer
               :display :flex
               :flex-direction :column
               :justify-content :center
               :height "3em"}

    [:&-status {:opacity 0}]

    [:&-hint {:color (:text-muted colors)
              :font-size "0.8em"}
     (at-break
       :tablet
       [:& {:font-size :inherit}])]

    [:&.win {:background-color (:valid-bckgnd colors)}]
    [:&.loss {:background-color (:error-bckgnd colors)}]
    [:&.win :&.loss
     [:.sd-game-edit-result-status {:opacity 1}]]

    (at-break
      :tablet
      [:& {:padding (:padding box-model)}])]


   [:.sd-input {:margin 0}
    [:&-checkbox
     [:.sd-input-label {:display :none}
      (at-break
        :tablet
        [:& {:display :initial}])]]]


   [:&-center {:width "1%"}
    [:.sd-input {:width "3.5em"}]]


   [:.sd-table

    [:th :td {:max-width "5em"
              :text-align :center
              :vertical-align :middle}]

    [:tbody
     [:th :td {:border 0}]]]])
