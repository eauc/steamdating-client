(ns steamdating.styles.5-components.round-summary
	(:require [garden.def :as gdef]
						[steamdating.styles.0-settings.colors :refer [colors]]
						[steamdating.styles.1-tools.table :refer [table]]))


(gdef/defstyles round-summary
	[:&-RoundsSummary
	 (table [:&-list])
	 [:&-nth-header {:white-space "nowrap"}
		[:a {:color (:text colors)
         :text-decoration "none"}]]
   [:&-lists {:font-size "0.8em"
              :vertical-align "middle"}
    [:&-ok {:background-color (:info-bckgnd colors)}]
    [:&-ko {:background-color (:warning-bckgnd colors)}]]
   [:&-game
    :&-name  {:cursor :pointer
              :white-space "nowrap"}]
	 [:&-win	{:background-color (:valid-bckgnd colors)}]
	 [:&-loss {:background-color (:error-bckgnd colors)}]])
