(ns steamdating.styles.5-components.toaster
	(:require [garden.def :as gdef]
						[steamdating.styles.0-settings.box-model :refer [box-model]]
						[steamdating.styles.0-settings.colors :refer [colors]]))


(def opacity
	0.75)


(gdef/defstyles toaster
	[:&-toaster {:bottom 0
							 :color (:text-inverted colors)
							 :font-weight "bold"
							 :left 0
							 :pointer-events "none"
							 :position "fixed"
							 :right 0
							 :text-align "center"
               :z-index 500}
	 [:&-content {:opacity 0
								:padding (:padding box-model)
								:transition "opacity 0.2s, background-color 0.2s"}
		[:&.success
		 :&.warn
		 :&.error
		 :&.info {:opacity 1}]
		[:&.success {:background-color (assoc (:accent colors) :alpha opacity)}]
		[:&.warn {:background-color (assoc (:warning colors) :alpha opacity)}]
		[:&.error {:background-color (assoc (:error colors) :alpha opacity)}]
		[:&.info {:background-color (assoc (:primary colors) :alpha opacity)}]]])
