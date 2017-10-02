(ns steamdating.styles.5-components.online-follow
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.1-tools.button :refer [button]]
            [steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles online-follow
  [:&-Online
   (button [:&Follow])])
