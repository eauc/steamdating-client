(ns steamdating.components.generics.input
  (:require [reagent.core :as reagent]))


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


(defn input
  [{:keys [element field label on-update value] :as base-props}]
  (let [current-value (reagent/atom value)
        on-update-debounced (debounce on-update 250)
        on-change #(let [new-value (-> % .-target .-value
                                       (cond-> (number? value) (js/Number)))]
                     (reset! current-value new-value)
                     (on-update-debounced field new-value))
        input-props (-> base-props
                        (dissoc :class :element :error :field :label :on-update)
                        (assoc :class "sd-Input-value"
                               :id field :name field
                               :placeholder label
                               :on-change on-change
                               :type (if (number? value) "number" "text")))]
    (fn input-component
      [{:keys [error]}]
      [:div.sd-Input {:class (when error "error")}
       (when label [:label {:for field} label])
       [(or element :input) (assoc input-props :value @current-value)]
       [:p.sd-Input-error
        (or error "No error")]])))
