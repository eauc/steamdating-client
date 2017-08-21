(ns steamdating.components.prompt.prompt
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.services.prompt]))


(defn render
  [_ {:keys [on-cancel on-update on-validate value]}]

  (let [on-form-click #(-> % .stopPropagation)
        on-form-submit (fn [event]
                         (doto event
                           (.preventDefault)
                           (.stopPropagation))
                         (on-validate))]
    (fn prompt-render
      [{:keys [message type value] :as state} _]

      [:div.sd-Prompt {:class (when state "sd-Prompt-show")}
       [:div.sd-Prompt-mask {:on-click on-cancel}
        [:form.sd-Prompt-content {:on-click on-form-click
                                  :on-submit on-form-submit}
         [:div.sd-Prompt-msg message]
         (when (= type :prompt)
           [input {:type (if (number? value) "number" "text")
                   :field [:value]
                   :state {:edit {:value value}}
                   :on-update on-update
                   :autofocus "autofocus"}])
         [:div.sd-Prompt-controls
          [:button.sd-Prompt-control-ok {:type "submit"}
           [:span "Ok "]
           [icon {:name "check"}]]
          (when (not= type :alert)
            [:button.sd-Prompt-control-cancel
             {:type "button"
              :on-click on-cancel}
             [:span "No "]
             [icon {:name "close"}]])]]]])))


(defn prompt
  []

  (let [state (re-frame/subscribe [:steamdating.prompt/prompt])
        on-cancel #(re-frame/dispatch [:steamdating.prompt/cancel])
        on-update #(re-frame/dispatch [:steamdating.prompt/update %2])
        on-validate #(re-frame/dispatch [:steamdating.prompt/validate])]
    (fn prompt-component
      []

      [render @state
       {:on-cancel on-cancel
        :on-update on-update
        :on-validate on-validate}])))
