(ns steamdating.styles.5-components.round-nth
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles round-nth
  [:&-round-nth


   [:&-faction
    :&-score
    :&-table {:width "1%"}]


   [:&-faction
    :&-score {:display :none}
    (at-break
      :tablet
      [:& {:display :table-cell}])]


   [:&-player
    [:&.win {:background-color (:valid-bckgnd colors)}]
    [:&.loss {:background-color (:error-bckgnd colors)}]
    [:&-list {:color (:text-muted colors)
              :font-size "0.8em"}]]


   [:tbody
    [:tr {:cursor :pointer}]]
   [:td :th {:vertical-align :middle
             :text-align :center}]
   [:.sd-faction-icon-label {:display :none}]])
