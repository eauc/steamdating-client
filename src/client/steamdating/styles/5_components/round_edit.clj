(ns steamdating.styles.5-components.round-edit
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))


(gdef/defstyles round-edit
  [:&-Round
    [:&Edit
     [:&-gamesList {:width "100%"
                    :border "0px solid transparent"}]
     [:&-info {:margin "0.5em 0 1em 0"
               :font-size "0.85em"
               :font-style "italic"}]
     [:&-error {:color (:error colors)}]
     [:input
      :select {:padding "0.3em 0"}
      (at-break
        :tablet
        [:& {:padding "0.5em"}])]
     (button [:&-suggest])]
   [:&GamesEdit
    [:&-table
     :&-faction {:width "1%"}]
    [:input {:max-width "3em"}]
    [:&-warning {:background-color (:warning-bckgnd colors)}]]])
