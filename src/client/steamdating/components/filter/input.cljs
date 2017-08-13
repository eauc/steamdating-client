(ns steamdating.components.filter.input
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.services.filters]))


(defn render-filter-input
  [value {:keys [name on-update]}]
  [input {:name (str "filter-" (clojure.core/name name))
          :on-update on-update
          :placeholder "Filter"
          :type "text"
          :value value}])


(defn filter-input
  [{:keys [name]}]
  (let [value (re-frame/subscribe [:steamdating.filters/filter name])
        on-update #(re-frame/dispatch [:steamdating.filters/set name %2])]
    (fn filter-component
      []
      [render-filter-input @value
       {:name name
        :on-update on-update}])))