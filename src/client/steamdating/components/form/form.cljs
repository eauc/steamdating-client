(ns steamdating.components.form.form
  (:require [re-frame.core :as re-frame]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defn form
  [{:keys [label on-submit state]} children]
  (let [valid? (form/valid? state)
        {:keys [base edit error]} state
        pristine? (= base edit)
        form-error (get error nil)]
    [:form.sd-form
     {:no-validate true
      :on-submit (fn [event]
                   (.preventDefault event)
                   (when valid? (on-submit))
                   false)}
     [:fieldset.group
      [:legend.legend label]
      children
      (when (and (not pristine?) (some? form-error))
        [:p.form-info.error form-error])
      [:button.submit {:type :submit}
       "submit"]]]))
