(ns steamdating.styles.5-components.player-file-imports
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles player-file-imports
  [:&-player-file-imports
   [:&-actions {:display :flex
                :flex-direction :column
                :align-item :stretch}

    [:.sd-button {:margin "0.5em 0"}]


    (at-break
      :tablet
      [:& {:flex-direction :row}


       [:.sd-button {:margin "0 1em"}]])]])
