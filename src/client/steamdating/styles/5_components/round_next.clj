(ns steamdating.styles.5-components.round-next
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles round-next
  [:&-round-next


   [:.sd-table
    [:td {:border 0
          :vertical-align :middle}]]


   [:.sd-input {:margin 0}]


   [:&-faction {:display :none}
    (at-break
      :tablet
      [:& {:display :table-cell}])
    [:.sd-faction-icon-label {:display :none}]]


   [:&-faction
    :&-table {:width "1%"}]


   [:&-faction
    :&-player
    :&-table
    [:&.warn {:background-color (:warning-bckgnd colors)}]
    [:&.error {:background-color (:error-bckgnd colors)}]]


   [:&-table
    [:.sd-input {:width "3.5em"}
     (at-break
       :tablet
       [:& {:width "4em"}])]]])
