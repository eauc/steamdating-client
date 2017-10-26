(ns steamdating.components.nav.menu
	(:require [re-frame.core :as re-frame]
						[reagent.core :as reagent]
						[steamdating.components.nav.link :refer [nav-link]]
						[steamdating.services.debug :refer [debug? log spy]]
            [steamdating.services.routes]
            [steamdating.services.ui]))


(defmulti nav-menu-content #(get-in % [:route :page]))


(defn content-height
  [content]
  (or
    (some-> content
            (.getElementsByClassName "content")
            (aget 0)
            (.getBoundingClientRect)
            (aget "height")
            (str "px")) 0))


(defn nav-menu
	[{:keys [show?]}]
	(let [state (re-frame/subscribe [:sd.ui/menu-route])
        local-state (reagent/atom {})]
		(fn nav-menu-render
			[]
      [:div.container {:style {:height (if (= :nav (:menu @state)) (:height @local-state) 0)}
                       :ref #(when (some? %)
                               (swap! local-state assoc :height (content-height %)))}
       (nav-menu-content @state)])))


(defmethod nav-menu-content :default
	[{ {:keys [hash]} :route}]
	[:div.content
	 (when debug?
		 [nav-link {:hash hash
								:target "#/home"}
			"Home"])
	 [nav-link {:hash hash
							:target "#/data"}
		"Data"]
	 [nav-link {:hash hash
							:target "#/online"}
		"Online"]
	 [nav-link {:hash hash
							:target "#/players"
							:active-on "#/players"}
		"Players"]
	 [nav-link {:hash hash
							:target "#/ranking"}
		"Ranking"]
	 [nav-link {:hash hash
							:target "#/rounds/all"
							:active-on "#/rounds"}
		"Rounds"]
	 [nav-link {:hash hash
							:target "#/settings"}
		"Settings"]])
