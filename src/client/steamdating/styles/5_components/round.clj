(ns steamdating.styles.5-components.round
  (:require [garden.def :as gdef]
            [garden.selectors :as gsel]
            [steamdating.styles.0-settings.break :refer [at-break break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles round
  [:&-Round {:max-width (:pc break)}
   (table [:&-list])
   [:&-list
    [:th
     :td {:text-align "center"}]
    [(gsel/td (gsel/nth-child "4"))
     (gsel/td (gsel/nth-last-child "4")) {:max-width "1em"}]
    [:thead
     :tbody {:cursor "pointer"}]]
   [:&GameRow
    :&Header
    [:&-table
     :&-faction
     :&-score {:width "1%"
               :white-space "nowrap"}]
    [:&-faction
     :&-score {:display "none"}
     (at-break
       :tablet
       [:& {:display "table-cell"}])]
    [:&-win  {:background-color (:valid-bckgnd colors)}]
    [:&-loss {:background-color (:error-bckgnd colors)}]]
   [:&Header
    [:&-icon {:opacity 0}
     [:&-show {:opacity 1}]]]])
