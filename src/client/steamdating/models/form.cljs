(ns steamdating.models.form
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as str]
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


(defn path->string
  [path]
  (str/join " " (map name path)))


(defn validate-error
  [spec edit]
  (let [expl (spec/explain-data spec edit)]
    (reduce #(let [path (:path %2)
                   name (path->string path)]
               (assoc-in %1 path (str "Invalid " name)))
            {} (:cljs.spec.alpha/problems expl))))


(defn validate
  [{:keys [edit] :as form} spec]
  (let [valid? (spec/valid? spec edit)
        error (when-not valid? (validate-error spec edit))]
    (debug/log "form/validate:" (expound-str spec edit))
    (assoc form :error error)))
