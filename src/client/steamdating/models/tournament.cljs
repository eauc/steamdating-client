(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.form :as form]
            [steamdating.models.player]
            [steamdating.models.online]))


(spec/def :sd.tournament.settings/tables-groups-size
  nat-int?)


(spec/def :sd.tournament/settings
  (spec/keys :req-un [:steamdating.tournament.settings/tables-groups-size]))


(spec/def :sd.tournament/version
  nat-int?)


(spec/def :sd.tournament/online
  :sd.online/tournament)


(spec/def :sd.tournament/tournament
  (spec/keys :req-un [:sd.player/players
                      :sd.rounds/rounds
                      :sd.tournament/settings
                      :sd.tournament/version]
             :opt-un [:sd.tournament/online]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :settings {:tables-groups-size 1}
   :version 1})


(defn validate-settings
  [form-state]
  (form/validate :sd.tournament/settings form-state))
