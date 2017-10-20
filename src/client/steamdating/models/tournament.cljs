(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.form :as form]
            [steamdating.models.player]
            [steamdating.models.online]
            [steamdating.models.round]))


(spec/def :steamdating.tournament/online
  :steamdating.online/tournament)


(spec/def :steamdating.tournament.settings/tables-groups-size
  nat-int?)


(spec/def :steamdating.tournament/settings
  (spec/keys :req-un [:steamdating.tournament.settings/tables-groups-size]))


(spec/def :steamdating.tournament/tournament
  (spec/keys :req-un [:steamdating.player/players
                      :steamdating.round/rounds
                      :steamdating.tournament/settings]
             :opt-un [:steamdating.tournament/online]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :settings {:tables-groups-size 1}
   :version 1})


(defn validate-settings
  [form-state]
  (form/validate form-state :steamdating.tournament/settings))
