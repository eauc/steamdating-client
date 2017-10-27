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
  [{:keys [autofocus? element on-update value]
    :or {element :input}}]
  (let [state (reagent/atom {:value value
                             :initial-value value
                             :last-value value})
        on-change (debounce on-update 250)
        on-ref #(when (and (some? %) (nil? (:input @state)))
                  (swap! state assoc :input %)
                  (when autofocus? (.focus %)))]
    (fn form-input-render
      [{:keys [error label name type value] :as props}]
      (when-not (= value (:last-value @state))
        (swap! state merge {:value value :last-value value}))
      (let [pristine? (= (:value @state) (:initial-value @state))
            show-error? (and (not pristine?) (some? error))]
        [:div.sd-input
         ;; [:p (with-out-str (cljs.pprint/pprint {:props props
         ;;                                        :state @state
         ;;                                        :pristine? pristine?
         ;;                                        :show-error? show-error?}))]
         (when (some? label)
           [:label.sd-input-label {:for name}
            label])
         [element
          (-> props
              (dissoc :autofocus? :element :error :label :on-update :value)
              (assoc :class (str "sd-input-value"
                                 (if show-error? " error" "")))
              (assoc :id name)
              (assoc :on-change #(let [raw-value (-> % .-target .-value)
                                       new-value (if (number? (:value @state))
                                                   (int raw-value)
                                                   raw-value)]
                                   (swap! state assoc :value new-value)
                                   (on-change new-value)))
              (assoc :ref on-ref)
              (assoc :type (if (some? type)
                             type
                             (if (number? (:value @state))
                               :number
                               :text)))
              (assoc :value (:value @state)))]
         (when show-error?
           [:p.sd-input-error error])]))))
