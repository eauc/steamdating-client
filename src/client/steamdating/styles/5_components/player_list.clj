(ns steamdating.styles.5-components.player-list
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.break :refer [at-break]]))


(gdef/defstyles player-list
  [:&-player-list {:flex-shrink 1
                   :overflow-x :auto
                   :overflow-y :hidden
                   :position :relative
                   :width "100%"}


   [:&-container {:height "100%"
                  :overflow-x :hidden
                  :overflow-y :auto
                  :width "100%"}]


   [:tbody
    [:tr {:cursor :pointer}]
    [:td {:vertical-align :middle}]
    [:td.lists {:font-size "0.8em"}]

    (at-break
      :tablet
      [:&
       [:td {:padding (:padding box-model)}]
       [:td.lists {:font-size :inherit}]])]])
