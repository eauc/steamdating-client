(ns steamdating.components.generics.input
  (:require [clojure.string :as s]
            [reagent.core :as reagent]
            [steamdating.models.form :as form]
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


(defmulti get-value #(:type %2))


(defmethod get-value :default
  [event]
  (-> event .-target .-value))


(defmethod get-value "number"
  [event]
  (-> event .-target .-value (js/Number)))


(defmethod get-value "select"
  [event {:keys [multiple]}]
  (if-not multiple
    (-> event .-target .-value)
    (-> event .-target .-options
        (js/Array.from)
        (.filter #(.-selected %))
        (.map #(.-value %))
        (js->clj))))


(defmethod get-value "checkbox"
  [event]
  (-> event .-target .-checked))


(defmulti render-value :type)


(defmethod render-value :default
  [{:keys [label placeholder] :as props}]
  [:input.sd-Input-value
   (assoc props
          :placeholder (or placeholder label))])


(defmethod render-value "checkbox"
  [{:keys [label placeholder value] :as props}]
  [:input.sd-Input-value
   (assoc props
          :placeholder (or placeholder label)
          :checked value)])


(defmethod render-value "select"
  [{:keys [label options multiple] :as props}]
  [:select.sd-Input-value
   (dissoc props :options :type)
   (when-not multiple [:option {:value ""} ""])
   (let [sorted-options (sort-by #(nth % 1) options)]
     (for [[value label] sorted-options]
       [:option {:key value
                 :value value}
        label]))])


(defmethod render-value "textarea"
  [{:keys [label placeholder] :as props}]
  [:textarea.sd-Input-value
   (assoc props :placeholder (or placeholder label))])


(def value-dom-props
  [:multiple :options :order :placeholder :required :type])


(defn value
  [{:keys [autofocus id multiple on-change] :as base-props}]
  (let [on-change #(on-change (get-value % base-props))
        props (-> base-props
                  (select-keys value-dom-props)
                  (assoc :id id
                         :name id
                         :on-change on-change))
        default-value (if multiple [] "")]
    (reagent/create-class
      {:display-name "Input"
       :component-did-mount
       (fn input-did-mount
         [component]
         (let [element (reagent/dom-node component)]
           (when autofocus
             (js/setTimeout #(.focus element) 100))))
       :reagent-render
       (fn [{:keys [class options value]}]
         [render-value
           (-> props
               (cond-> (not (nil? options)) (assoc :options options))
               (assoc :class class
                      :value (if-not (nil? value) value default-value)))])})))


(defmulti render-input :type)


(defmethod render-input :default
  [{:keys [class error id label] :as props}]
  [:div.sd-Input {:class class}
   (when label
     [:label {:for id}
      label])
   [value props]
   [:p.sd-Input-info
    (or error "No error")]])


(defmethod render-input "checkbox"
  [{:keys [class error id label] :as props}]
  [:div.sd-Input {:class class}
   [:label {:for id}
    [value props]
    [:span (str " " label)]]
   [:p.sd-Input-info
    (or error "No error")]])


(defn input
  [{:keys [field on-update state] :as base-props}]
  (let [current-value (reagent/atom (form/field-value state field))
        pristine (reagent/atom true)
        id (s/join "." (map name field))
        on-update-debounced (debounce on-update 250)
        on-change (fn [new-value]
                    (reset! current-value new-value)
                    (reset! pristine false)
                    (on-update-debounced field new-value))]
    (fn input-component
      [{:keys [state] :as props}]
      (let [has-value? (or (and (seq? @current-value) (not-empty @current-value))
                           (and (not (seq? @current-value)) (not (nil? @current-value))))
            clear? (and @pristine (not has-value?))
            error (form/field-error state field)
            show-error? (and (not clear?) error)
            show-valid? (and (not clear?) (not error))
            class (s/join " " (remove nil? [(when @pristine "pristine")
                                            (when show-valid? "valid")
                                            (when show-error? "error")]))]
        [render-input
         (assoc (select-keys props [:autofocus :label :multiple :order :options :placeholder :required :type])
                :id id
                :class class
                :error error
                :on-change on-change
                :value @current-value)]))))
