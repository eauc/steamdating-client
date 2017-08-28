(ns steamdating.components.generics.textarea
  (:require [reagent.core :as reagent]
            [steamdating.components.generics.input :refer [->input-component]]
            [steamdating.models.form :as form]))


(defn render-textarea
  [{:keys [on-change] :as props}]
  [:textarea.sd-Input-value props])


(def textarea
  (->input-component {:render-value render-textarea}))
