(ns steamdating.components.nav.actions
	(:require [re-frame.core :as re-frame]
						[reagent.core :as reagent]
						[steamdating.components.nav.toggle :refer [nav-toggle]]
            [steamdating.services.routes]
            [steamdating.services.ui]))


(defmulti nav-actions-content :page)


(defn nav-actions
	[]
	(let [state (re-frame/subscribe [:sd.ui.nav/menu])]
		(fn nav-actions-render
			[]
			(nav-actions-content @state))))


(defmethod nav-actions-content :default
	[{:keys [menu]}]
	[:div.content
   [nav-toggle {:menu menu}]])
