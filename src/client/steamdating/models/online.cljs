(ns steamdating.models.online
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.online/token
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/_id
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/updatedAt
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/name
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/date
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/user
  (spec/and string? not-empty))


(spec/def :steamdating.online.tournament/link
  (spec/and string? not-empty))


(spec/def :steamdating.online/edit
  (spec/keys :req-un [:steamdating.online.tournament/name
                      :steamdating.online.tournament/date]))


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
