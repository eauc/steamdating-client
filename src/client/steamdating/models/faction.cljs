(ns steamdating.models.faction
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.faction/name
  (spec/and string? not-empty))


(spec/def :steamdating.faction/icon
  string?)


(spec/def :steamdating.faction/casters
  (spec/map-of keyword? string?))


(spec/def :steamdating.faction/faction
  (spec/keys :req-un [:steamdating.faction/name
                      :steamdating.faction/icon
                      :steamdating.faction/casters]))


(spec/def :steamdating.faction/factions
  (spec/map-of keyword? :steamdating.faction/faction))


(defn names
  [factions]
  (reduce (fn [result [key {:keys [name]}]]
            (assoc result key name))
          {} factions))


(defn casters
  [factions key]
  (let [{:keys [casters]} (get factions (keyword key))]
    (reduce (fn [result [key value]]
              (assoc result key (str (name key) " (" value ")")))
            {} casters)))


(defn icon
  [factions key]
  (let [{:keys [icon]} (get factions (keyword key))]
    (when icon (str "/data/icons/" icon))))


(defn cc-factions
  [factions]
  (into
    {}
    (map (fn [[key {:keys [name conflict-chamber]}]]
           [(or conflict-chamber name) (cljs.core/name key)]) factions)))


(defn t3-factions
  [factions]
  (into
    {}
    (map (fn [[key {:keys [name t3-fr]}]]
           [(or t3-fr name) (cljs.core/name key)]) factions)))
