(ns steamdating.styles.5-components.ranking-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.scrollable-table :refer [scrollable-table]]))


(gdef/defstyles ranking-list
  [:&-ranking-list


   scrollable-table


   [:&-name {:cursor :pointer}]


   [:.sd-faction-icon
    [:&-label {:display :none}
     (at-break
       :tablet
       [:& {:display :initial}])]]


   [:&-score {:text-align :center
              :width "1%"}]


   [:&-drop {:background-color :transparent
             :border 0
             :cursor :pointer
             :height "2.8em"
             :text-align :left}
    [:&:active
     :&:focus {:outline 0}]

    [:&-hint {:color (:text-muted colors)
              :display :none
              :font-size "0.8em"}]]

   [:&-player.droped
    [:td {:background-color (:disabled colors)}]
    [:.sd-ranking-list-drop-hint {:display :initial}]]


   [:.sd-table
    [:tbody
     [:.sd-ranking-list-drop-after {:padding 0}]

     [:td {:vertical-align :middle}]]]

   [:&-overlay.sd-table
    [:.sd-ranking-list-player.droped
     [:td :th {:background-color (:disabled colors)}]]]])
