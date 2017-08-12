(ns steamdating.components.generics.form
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.models.form :as form]))


(defn render-form
  [state {:keys [label name spec submit update-field]}]
  (fn [_ {:keys [error]} & children]
    (let [valid? (and (not error) (form/is-valid @state))
          on-submit (fn [event]
                      (.preventDefault event)
                      (when valid? (submit))
                      false)]
      [:form.sd-Form {:no-validate true
                      :on-submit on-submit}
       [:fieldset.sd-Form-group
        [:legend.sd-Form-legend label]
        (doall
          (for [[node props] children]
            (let [name (:name props)]
              [node (assoc props
                           :key name
                           :value (form/field-value @state name)
                           :error (or (:error props)
                                      (form/field-error @state name))
                           :on-update update-field)])))
        [:button.sd-Form-submit
         {:class (when-not valid? "sd-Form-disabled")
          :type "submit"
          :value "submit"}
         [icon {:name "check"}]]]])))


(defn form
  [{:keys [name on-submit spec]} & children]
  (let [state (re-frame/subscribe [:steamdating.forms/validate name spec])
        submit #(when (form/is-valid @state)
                  (re-frame/dispatch [on-submit @state]))
        update-field #(re-frame/dispatch
                        [:steamdating.forms/update name %1 %2])]
    (fn form-component [props & children]
      (apply conj
             [render-form state (assoc props
                                       :submit submit
                                       :update-field update-field)]
             children))))
