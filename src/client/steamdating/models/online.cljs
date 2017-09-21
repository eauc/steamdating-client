(ns steamdating.models.online
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.online/token
  string?)


(spec/def :steamdating.online.tournament/_id
  string?)


(spec/def :steamdating.online.tournament/updatedAt
  string?)


(spec/def :steamdating.online.tournament/name
  string?)


(spec/def :steamdating.online.tournament/date
  string?)


(spec/def :steamdating.online.tournament/user
  string?)


(spec/def :steamdating.online.tournament/link
  string?)


(spec/def :steamdating.online/tournament
  (spec/keys :req-un [:steamdating.online.tournament/_id
                      :steamdating.online.tournament/updatedAt
                      :steamdating.online.tournament/name
                      :steamdating.online.tournament/date
                      :steamdating.online.tournament/user
                      :steamdating.online.tournament/link]))


(spec/def :steamdating.online/tournaments
  (spec/or :list (spec/coll-of :steamdating.online/tournament :kind vector?)
           :error #{:failed}))


(spec/def :steamdating.online/online
  (spec/keys :opt-un [:steamdating.online/token
                      :steamdating.online/tournaments]))
