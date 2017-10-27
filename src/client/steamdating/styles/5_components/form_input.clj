(ns steamdating.styles.5-components.form-input
	(:require [garden.compiler :as gcomp]
						[garden.def :as gdef]
						[garden.selectors :as gsel]
						[steamdating.styles.0-settings.box-model :refer [box-model]]
						[steamdating.styles.0-settings.colors :refer [colors]]))

(gdef/defstyles form-input
	[:&-input {:display "flex"
						 :flex-flow "column nowrap"
						 :margin-bottom (:padding box-model)}


	 [:&-label {:font-weight :bold
							:margin-bottom (:padding box-model)
							:margin-top (:padding box-model)}]

	 [:&-value {:background-color "white"
							:border (:border box-model)
							:padding "0.5em"
							:line-height "1.35em"}
		[(gsel/& (gsel/attr :required)) {:border-width "1px 1px 1px 5px"}]
		[:&:focus {:border-color (:focus colors)
							 :box-shadow (str "0 0 4px " (gcomp/render-css (:focus-shadow colors)))
							 :outline "none"}]
		[:&.error {:border-color (:error colors)
							 :color (:error colors)}
		 [:&:focus { :outline-color "red"}]]]


	 [:select.sd-input-value {:width "100%"}]

	 [:&-error {:color (:error colors)
							:font-size "0.85em"
							:font-style "italic"
							:margin-bottom (:padding box-model)
							:margin-left (:padding box-model)
							:margin-top (:padding box-model)}]])
