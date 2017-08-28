(ns steamdating.models.form
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [expound.alpha :refer [expound-str]]
            [steamdating.services.debug :as debug]))


(spec/def :steamdating.form/value
  map?)


(spec/def :steamdating.form/base
  :steamdating.form/value)


(spec/def :steamdating.form/edit
  :steamdating.form/value)


(spec/def :steamdating.form/form
  (spec/keys :req-un [:steamdating.form/base
                      :steamdating.form/edit]))


(defn create
  [value]
  {:base value
   :edit value})


(defn field-value
  ([form field default-value]
   (get-in form (into [:edit] field) default-value))
  ([form field]
   (field-value form field nil)))


(defn field-error
  [form field]
  (get-in form (into [:error] field)))


(defn is-valid
  [{:keys [error]}]
  (not error))


(defn field->id
  [field]
  (s/join "." (map #(if (keyword? %) (name %) %) field)))


(defn state->class
  [{:keys [current-value error pristine?]}]
  (let [has-value? (or (and (seq? current-value) (not-empty current-value))
                       (and (not (seq? current-value)) (not (nil? current-value))))
        clear? (and pristine? (not has-value?))
        show-error? (and (not clear?) error)
        show-valid? (and (not clear?) (not error))]
    (s/join " " (remove nil? [(when pristine? "pristine")
                              (when show-valid? "valid")
                              (when show-error? "error")]))))


(defn spec-path->desc
  [path]
  (s/join " " (map name path)))


(defn validate-error
  [spec edit]
  (let [expl (spec/explain-data spec edit)]
    (reduce #(let [path (:path %2)
                   name (spec-path->desc path)]
               (assoc-in %1 path (str "Invalid " name)))
            {} (:cljs.spec.alpha/problems expl))))


(defn validate
  [{:keys [edit] :as form} spec]
  (let [valid? (spec/valid? spec edit)
        error (when-not valid? (validate-error spec edit))]
    (debug/log "form/validate:" (expound-str spec edit))
    (assoc form :error error)))
