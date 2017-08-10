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
  [{:keys [label] :as props}]
  [:input.sd-Input-value
   (assoc props
          :placeholder label)])


(defmethod render-value "checkbox"
  [{:keys [label value] :as props}]
  [:input.sd-Input-value
   (assoc props
          :placeholder label
          :checked value)])


(defmethod render-value "select"
  [{:keys [label options multiple] :as props}]
  [:select.sd-Input-value
   (dissoc props :options :type)
   (when-not multiple [:option {:value ""} ""])
   (for [[value label] options]
     [:option {:key value
               :value value}
      label])])


(defmethod render-value "textarea"
  [{:keys [label] :as props}]
  [:textarea.sd-Input-value
   (assoc props :placeholder label)])


(defn value
  [{:keys [autofocus label multiple on-change] :as base-props}]
  (let [on-change #(on-change (get-value % base-props))
        props (-> base-props
                  (select-keys [:type :required :order :options :multiple :placeholder])
                  (assoc :id (name (:name base-props))
                         :name (name (:name base-props))
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
       (fn [{:keys [value]}]
         (render-value
           (assoc props
                  :value (if-not (nil? value) value default-value))))})))


(defmulti render-input :type)


(defmethod render-input :default
  [{:keys [class error label] :as props}]
  [:div.sd-Input {:class class}
   (when label
     [:label {:for (name (:name props))}
      label])
   [value props]
   [:p.sd-Input-error
    (or error "No error")]])


(defmethod render-input "checkbox"
  [{:keys [class error label] :as props}]
  [:div.sd-Input {:class class}
   [:label {:for (name (:name props))}
    [value props]
    [:span (str " " label)]]
   [:p.sd-Input-error
    (or error "No error")]])


(defn input
  [{:keys [on-update value] :as base-props}]
  (let [current-value (reagent/atom value)
        pristine (reagent/atom true)
        on-update-debounced (debounce on-update 250)
        on-change (fn [new-value]
                    (reset! current-value new-value)
                    (reset! pristine false)
                    (on-update-debounced (:name base-props) new-value))]
    (fn input-component
      [{:keys [error] :as props}]
      (let [has-value? (or (and (seq? @current-value) (not-empty @current-value))
                           (and (not (seq? @current-value)) (not (nil? @current-value))))
            clear? (and @pristine (not has-value?))
            show-error? (and (not clear?) error)
            show-valid? (and (not clear?) (not error))
            class (str (when @pristine "pristine ")
                       (when show-valid? "valid ")
                       (when show-error? "error "))]
        (render-input (assoc props
                             :class class
                             :on-change on-change
                             :pristine @pristine
                             :value @current-value))))))
