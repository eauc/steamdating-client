(ns steamdating.components.generics.checkbox
  (:require [reagent.core :as reagent]
            [steamdating.models.form :as form]
            [steamdating.components.generics.input :as input]))


(defn get-value
  [event]
  (-> event .-target .-checked))


(defn render-checkbox
  [{:keys [on-change value] :as props}]
  [:input.sd-Input-value
   (assoc props
          :type "checkbox"
          :checked value)])


(defn checkbox
  [{:keys [field state] :as props}]
  (let [current-value (reagent/atom (form/field-value state field))
        pristine (reagent/atom true)
        static-props (input/static-props current-value pristine get-value false props)]
    (fn [current-props]
      (let [{:keys [class error id label] :as dyn-props}
            (merge static-props
                   (input/dynamic-props current-value pristine false current-props))]
        [:div.sd-Input {:class class}
         [:label {:for id}
          [render-checkbox (dissoc dyn-props :error)]
          [:span (str " " label)]]
         [:p.sd-Input-info
          (or error "No error")]]))))
