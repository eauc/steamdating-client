(ns steamdating.models.faction
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.faction/name
  (spec/and string? not-empty))


(spec/def :sd.faction/icon
  string?)


(spec/def :sd.faction/casters
  (spec/map-of keyword? string?))


(spec/def :sd.faction/faction
  (spec/keys :req-un [:sd.faction/name
                      :sd.faction/icon
                      :sd.faction/casters]))


(spec/def :sd.faction/factions
  (spec/map-of keyword? :sd.faction/faction))


(spec/def :sd.faction/names
  (spec/map-of keyword? :sd.faction/name))


(defn names
  [factions]
  (reduce (fn [result [key {:keys [name]}]]
            (assoc result key name))
          {} factions))


(spec/def :sd.faction/casters
  (spec/map-of keyword? string?))


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


(defn icons
  [factions]
  (into {} (map (fn [[k v]] [k (:icon v)]) factions)))


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
