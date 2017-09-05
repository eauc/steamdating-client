(ns steamdating.styles.5-components.game-edit
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.colors :refer[colors]]
            [steamdating.styles.1-tools.table :refer[table]]))

(gdef/defstyles game-edit
  [:&-GameEdit
   (table [:&])
   [:& {:text-align "center"}]
   [:.sd-Input {:padding 0
                :max-width "35vw"}]
   [:&-table {:width "1%"}
    [:input {:max-width "2.25em"}]]
   [:&-win-loss {:cursor "pointer"}]
   [:&-win {:background-color (:valid-bckgnd colors)}]
   [:&-loss {:background-color (:error-bckgnd colors)}]])
