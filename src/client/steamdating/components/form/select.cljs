(ns steamdating.components.form.select
  (:require [reagent.core :as reagent]
            [steamdating.services.debug :as debug]))


(defn get-value
  [{:keys [multiple]} target]
  (if-not multiple
    (-> target .-value)
    (-> target .-options
        (js/Array.from)
        (.filter #(.-selected %))
        (.map #(.-value %))
        (js->clj))))


(defn form-select
  [{:keys [autofocus? on-update value]}]
  (let [state (reagent/atom {:initial-value value})
        on-ref #(when (and (some? %) (nil? (:input @state)))
                  (swap! state assoc :input %)
                  (when autofocus? (.focus %)))]
    (fn form-select-render
      [{:keys [error label multiple name options value] :as props}]
      (let [pristine? (= value (:initial-value @state))
            show-error? (and (not pristine?) (some? error))]
        [:div.sd-input
         (when (some? label)
           [:label {:for name} label])
         [:select.value
          (-> props
              (dissoc :autofocus? :error :label :on-update :options :value)
              (assoc :class (if show-error? "error"))
              (assoc :id name)
              (assoc :on-change #(let [new-value (get-value props (.-target %))]
                                   (on-update new-value)))
              (assoc :ref on-ref)
              (assoc :value value))
          (when-not multiple
            [:option {:value nil} ""])
          (for [[value name] options]
            [:option {:key value
                      :value value} name])]
         (when show-error?
           [:p.input-info.error error])]))))
