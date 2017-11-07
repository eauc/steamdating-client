(ns steamdating.styles.5-components.ranking-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles ranking-list
  [:&-ranking-list {:overflow :hidden
                    :position :relative}


   [:&-name {:cursor :pointer}]


   [:.sd-faction-icon {:display :block}
    (at-break
      :tablet
      [:& {:display :flex}])
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

    [:.sd-ranking-list-drop-after {:padding 0}]
    [:td {:vertical-align :middle}]]


   [:&-scrollable {:overflow-x :auto}]


   [:&-overlay.sd-table {:left 0
                         :pointer-events :none
                         :position :absolute
                         :top 0}

    [:caption {:background-color :white
               :pointer-events :all}]

    [:td :th {:background-color :white}
     ["&:nth-child(n+3)" {:opacity :0}]]

    [:.sd-ranking-list-player.droped
     [:td :th {:background-color (:disabled colors)}]]]])
