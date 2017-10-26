(ns steamdating.components.prompt.prompt
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.prompt]))


(defn prompt-render
  [{:keys [state on-cancel on-update on-validate]}]
  (let [{:keys [message type value]} state]
    [:div.prompt {:class (when state "show")}
     [:form.content
      {:on-click #(-> % .stopPropagation)
       :on-submit (fn [event]
                    (doto event
                      (.preventDefault)
                      (.stopPropagation))
                    (on-validate))}
      [:div.message message]
      (when (= type :prompt)
        [form-input {:type (if (number? value) :number :text)
                     :value value
                     :on-update on-update
                     :autofocus? true}])
      [:div.controls
       [:button.sd-button.success
        [:span "Ok "]
        [icon {:name "check"}]]
       (when (not= type :alert)
         [:button.sd-button
          {:type "button"
           :on-click on-cancel}
          [:span "No "]
          [icon {:name "x"}]])]]]))


(defn prompt
  []
  (let [state @(re-frame/subscribe [:sd.prompt/prompt])
        on-cancel #(re-frame/dispatch [:sd.prompt/cancel])
        on-update #(re-frame/dispatch [:sd.prompt/update-value %])
        on-validate #(re-frame/dispatch [:sd.prompt/validate])]
    [prompt-render
     {:state state
      :on-cancel on-cancel
      :on-update on-update
      :on-validate on-validate}]))
