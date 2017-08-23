(ns steamdating.components.filter.input
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.services.filters]))


(defn render-filter-input
  [value {:keys [name on-update]}]
  [input {:type "text"
          :on-update on-update
          :placeholder "Filter"
          :field [:filter name]
          :state {:edit {:filter {name value}}}}])


(defn filter-input
  [{:keys [name]}]
  (let [value @(re-frame/subscribe [:steamdating.filters/filter name])
        on-update #(re-frame/dispatch [:steamdating.filters/set name %2])]
    [render-filter-input value
     {:name name
      :on-update on-update}]))
