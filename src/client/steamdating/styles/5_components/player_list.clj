(ns steamdating.styles.5-components.player-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]))


(gdef/defstyles player-list
  [:&-player-list {:overflow :hidden
                   :position :relative}


   [:&-scrollable {:overflow-x :auto}]


   [:&-overlay {:left 0
                :pointer-events :none
                :position :absolute
                :top 0}

    [:caption {:background-color :white
               :pointer-events :all}]

    [:th :td {:background-color :white}
     ["&:nth-child(n+2)" {:opacity :0}]]]


   [:.sd-table
    [:tbody
     [:tr {:cursor :pointer}]

     [:td {:vertical-align :middle}]

     [:td.lists {:font-size "0.8em"}]

     (at-break
       :tablet
       [:&
        [:td {:padding (:padding box-model)}]

        [:td.lists {:font-size :inherit}]])]]])
