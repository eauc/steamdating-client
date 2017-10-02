(ns steamdating.styles.5-components.online-tournaments
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.1-tools.button :refer [button]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles online-tournaments
  [:&-Online
   [:&Tournaments {:display :flex
                   :flex-direction :row
                   :justify-content :center
                   :width "100%"}
    [:&-loaded {:width "100%"}]
    (table [:&-list])
    [:&-list
     [:tbody [:tr {:vertical-align :middle}]]]
    [:&-item {:cursor :pointer}]
    [:&-retry {:text-align :center}]
    [:&-updatedAt {:display :none
                   :font-style :italic
                   :font-size "0.8em"}
     (at-break
       :tablet
       [:& {:display :table-cell}])]
    (button [:&-action])
    [:&-action-text {:display :none}
     (at-break
       :tablet
       [:& {:display :inline}])]]
   (button [:&Button])
   [:&Button {:width "100%"}
    (at-break
      :tablet
      [:& {:padding-left "1em"
           :padding-right "1em"
           :width :auto}])]])
