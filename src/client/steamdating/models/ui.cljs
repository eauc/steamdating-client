(ns steamdating.models.ui
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.route]))


(spec/def :sd.ui/menu
  (spec/nilable keyword?))


(spec/def :sd.ui/ui
  (spec/keys :req-un [:sd.ui/menu]))


(spec/def :sd.ui.nav/menu
  (spec/keys :req-un [:sd.ui/menu
                      :sd.route/route]))


(defn ->ui
  []
  {:menu nil})
