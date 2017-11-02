(ns steamdating.components.form.checkbox
  (:require [reagent.core :as reagent]))


(defn form-checkbox
  [{:keys [autofocus? on-update value]}]
  (let [state (reagent/atom {:initial-value value})
        on-ref #(when (and (some? %) (nil? (:input @state)))
                  (swap! state assoc :input %)
                  (when autofocus? (.focus %)))]
    (fn form-checkbox-render
      [{:keys [error label name value] :as props}]
      (let [pristine? (= value (:initial-value @state))
            show-error? (and (not pristine?) (some? error))]
        [:div.sd-input.sd-input-checkbox
         [:label {:for name}
          [:input.sd-input-value
           (-> props
               (dissoc :autofocus? :error :label :on-update :value)
               (assoc :class (if show-error? "error"))
               (assoc :id name)
               (assoc :on-change #(let [new-value (-> % .-target .-checked)]
                                    (on-update new-value)))
               (assoc :ref on-ref)
               (assoc :type :checkbox)
               (assoc :checked value))]
          [:span.sd-input-label label]]
         (when show-error?
           [:p.sd-input-error error])]))))
