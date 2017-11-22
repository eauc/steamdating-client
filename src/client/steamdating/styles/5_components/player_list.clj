(ns steamdating.styles.5-components.player-list
	(:require [garden.def :as gdef]
						[steamdating.styles.0-settings.box-model :refer [box-model]]
						[steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.1-tools.scrollable-table :refer [scrollable-table]]))


(gdef/defstyles player-list
	[:&-player-list

   scrollable-table

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
