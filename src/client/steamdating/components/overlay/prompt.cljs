(ns steamdating.components.overlay.prompt
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.services.prompt]))


(defn prompt-render
  [{:keys [state on-cancel on-update on-validate]}]
  (let [{:keys [message type value]} state]
    [:form.sd-prompt
     {:class (when (some? state) "show")
      :on-click #(-> % .stopPropagation)
      :on-submit (fn [event]
                   (doto event
                     (.preventDefault)
                     (.stopPropagation))
                   (on-validate))}
     [:div.sd-prompt-message message]
     (when (= type :prompt)
       [form-input {:autofocus? true
                    :on-update on-update
                    :name "value"
                    :type (if (number? value) :number :text)
                    :value value}])
     [:div.sd-prompt-controls
      [button {:class "success"
               :icon "check"
               :label "Ok"
               :type :submit}]
      (when (not= type :alert)
        [button {:icon "x"
                 :label "No"
                 :on-click on-cancel}])]]))


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
