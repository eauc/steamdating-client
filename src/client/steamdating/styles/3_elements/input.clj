(ns steamdating.styles.3-elements.input
	(:require [garden.def :as gdef]))


(gdef/defstyles input
	[:select
   "input[type='date']" {:-webkit-appearance :textfield
                         :-moz-appearance :none
                         :appearance :none
                         :line-height :normal
                         :min-height "2em"}])
