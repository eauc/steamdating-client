(ns steamdating.components.form.form
  (:require [re-frame.core :as re-frame]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defn form
  [{:keys [label on-submit state]} children]
  (let [valid? (form/valid? state)
        {:keys [base edit error warn]} state
        pristine? (= base edit)
        form-error (get error nil)
        form-warn (get warn nil)]
    [:form.sd-form
     {:no-validate true
      :on-submit (fn [event]
                   (.preventDefault event)
                   (when valid? (on-submit))
                   false)}
     [:fieldset.sd-form-group
      [:legend.sd-form-legend label]
      children
      (when (and (not pristine?) (some? form-error))
        (if (map? form-error)
          (for [[key error] form-error]
            [:p.sd-form-error {:key key} error])
          [:p.sd-form-error form-error]))
      (when (and (not pristine?) (some? form-warn))
        (for [[key warn] form-warn]
          [:p.sd-form-warn {:key key} warn]))
      [:button.sd-form-submit {:type :submit}
       "submit"]]]))
