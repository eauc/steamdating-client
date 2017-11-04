(ns steamdating.styles.5-components.round-summary
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles round-summary
  [:&-round-summary {:overflow :hidden
                     :position :relative}


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
    [:&.droped {:background-color (:disabled colors)}]]


   [:&-opponent {:white-space :nowrap}]


   [:&-list {:color (:text-muted colors)
             :font-size "0.8em"}]


   [:.sd-table
    [:tbody
     [:tr:hover {:background-color :inherit}]

     [:td {:vertical-align :middle}]

     [:.sd-round-summary-lists {:background-color (:warning-bckgnd colors)
                                :font-size "0.8em"}
      [:&.ok {:background-color (:info-bckgnd colors)}]]]]


   [:.sd-faction-icon-label {:display :none}]


   [:&-scrollable {:overflow-y :auto}]


   [:&-overlay.sd-table {:left 0
                         :pointer-events :none
                         :position :absolute
                         :top 0}

    [:caption {:background-color :white
               :pointer-events :all}]

    [:td :th {:background-color :white}
     ["&:nth-child(n+3)" {:opacity :0}]]]])
