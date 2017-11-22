(ns steamdating.styles.5-components.round-summary
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.scrollable-table :refer [scrollable-table]]))


(gdef/defstyles round-summary
  [:&-round-summary

   scrollable-table

   [:&-rank
    :&-faction {:width "1%"}]


   [:&-player {:cursor :pointer}]


   [:&-faction
    :&-lists
    :&-rank {:display :none}
    (at-break
      :tablet
      [:& {:display :table-cell}])]


   [:&-round {:cursor :pointer}
    [:&.win {:background-color (:valid-bckgnd colors)}]
    [:&.loss {:background-color (:error-bckgnd colors)}]
    [:&.droped {:background-color (:disabled colors)}]

    [:&-content {:display :flex
                 :flex-direction :column
                 :justify-content :center
                 :min-height "2.6em"
                 :padding-left (:padding box-model)
                 :padding-right (:padding box-model)}]]


   [:&-opponent {:white-space :nowrap}]


   [:&-list {:color (:text-muted colors)
             :font-size "0.8em"}]


   [:.sd-table
    [:tbody
     [:td {:vertical-align :middle}]

     [:.sd-round-summary-lists {:background-color (:warning-bckgnd colors)
                                :font-size "0.8em"}
      [:&.ok {:background-color (:info-bckgnd colors)}]]]]


   [:.sd-faction-icon-label {:display :none}]])
