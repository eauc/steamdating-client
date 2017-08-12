(ns steamdating.models.player
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.player/name
  (spec/and string? not-empty))


(spec/def :steamdating.player/origin
  (spec/and string?))


(spec/def :steamdating.player/faction
  (spec/and string?
            not-empty))


(spec/def :steamdating.player/list
  (spec/and string?
            not-empty))


(spec/def :steamdating.player/lists
  (spec/coll-of :steamdating.player/list :kind vector?))


(spec/def :steamdating.player/notes
  (spec/and string?))


(spec/def :steamdating.player/player
  (spec/keys :req-un [:steamdating.player/name]
             :opt-un [:steamdating.player/origin
                      :steamdating.player/faction
                      :steamdating.player/lists
                      :steamdating.player/notes]))


(spec/def :steamdating.player/players
  (spec/coll-of :steamdating.player/player :kind vector?))


(defn add
  [players player]
  (if (not-empty (find #(= (:name %) (:name player)) players))
    players
    (conj players player)))


(defn names
  [players]
  (set (map :name players)))
