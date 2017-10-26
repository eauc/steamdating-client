(ns steamdating.components.form.input
  (:require [reagent.core :as reagent]
            [steamdating.services.debug :as debug]))


(defn debounce [fun ms]
  (let [timeout (atom nil)]
    (fn [& args]
      (let [caller (fn []
                     (reset! timeout nil)
                     (apply fun args))]
        (when @timeout
          (.clearTimeout js/window @timeout))
        (reset! timeout
                (.setTimeout js/window caller ms))))))


(defn form-input
  [{:keys [autofocus? on-update value]}]
  (let [state (reagent/atom {:value value
                             :last-value value})
        on-change (debounce on-update 250)
        on-ref #(when (and (some? %) (nil? (:input @state)))
                  (swap! state assoc :input %)
                  (when autofocus? (.focus %)))]
    (fn form-input-render
      [{:keys [type value] :as props}]
      (when-not (= value (:last-value @state))
        (swap! state merge {:value value :last-value value}))
      [:div.sd-input
       [:input.value
        (-> props
            (dissoc :autofocus? :on-update)
            (assoc :on-change #(let [raw-value (-> % .-target .-value)
                                     new-value (if (= type :number)
                                                 (int raw-value)
                                                 raw-value)]
                                 (swap! state assoc :value new-value)
                                 (on-change new-value)))
            (assoc :ref on-ref)
            (assoc :value (:value @state)))]])))
