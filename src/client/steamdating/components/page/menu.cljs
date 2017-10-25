(ns steamdating.components.page.menu
	(:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
						[steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu-toggle :refer [page-menu-toggle]]
            [steamdating.services.debug :as debug]))


(defn menu-width
  [element]
  (or
    (some-> element
            (.getBoundingClientRect)
            (aget "width")
            (str "px")) "0px"))


(defmulti page-menu-items #(get-in % [:route :page]))


(defn page-menu
	[]
	(let [state (re-frame/subscribe [:sd.ui/menu-route])
        local-state (reagent/atom {})]
		(fn page-menu-render
			[]
      (let [items (page-menu-items @state)
            show? (= :page (:menu @state))
            {:keys [width] :or {width "0px"}} @local-state]
        (if (empty? items)
          [:div]
          [:div.menu {:style {:right (if show? "0px" (str "-" width))}
                      :ref #(when %
                              (swap! local-state assoc :width (menu-width %)))}
           [page-menu-toggle {:menu-width width
                              :show? show?}]
           items])))))


(defmethod page-menu-items :default
  []
  (list))
