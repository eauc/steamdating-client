(ns steamdating.models.online
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.models.filter]
            [steamdating.models.notification]
            [steamdating.services.debug :refer [debug?]]))


(spec/def :sd.online.user.auth/token
  (spec/and string? not-empty))


(spec/def :sd.online.user/auth
  (spec/keys :opt-un [:sd.online.user.auth/token]))


(spec/def :sd.online/user
  (spec/keys :opt-un [:sd.online.user/auth]))


(spec/def :sd.online.user/status-sub
  #{:logged :not-logged})


(spec/def :sd.online.tournament/_id
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/updatedAt
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/name
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/date
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/user
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/link
  (spec/and string? not-empty))


(spec/def :sd.online.tournament/edit
  (spec/keys :req-un [:sd.online.tournament/name
                      :sd.online.tournament/date]))


(spec/def :sd.online/tournament
  (spec/keys :req-un [:sd.online.tournament/_id
                      :sd.online.tournament/updatedAt
                      :sd.online.tournament/name
                      :sd.online.tournament/date
                      :sd.online.tournament/user
                      :sd.online.tournament/link]))


(spec/def :sd.online.tournaments/list
  (spec/coll-of :sd.online/tournament :kind vector?))


(spec/def :sd.online.tournaments/status
  #{:init :loading :success :error})


(spec/def :sd.online/tournaments
  (spec/keys :opt-un [:sd.online.tournaments/list
                      :sd.online.tournaments/status]))


(spec/def :sd.online.tournaments/filter
  :sd.filter/value)


(spec/def :sd.online/tournaments-sub
  (spec/keys :opt-un [:sd.online.tournaments/list
                      :sd.online.tournaments/status
                      :sd.online.tournaments/filter
                      :sd.sort/sort]))


(spec/def :sd.online.tournament/status-sub
  #{:online :offline})


(spec/def :sd.online.follow/show?
  boolean?)


(spec/def :sd.online.follow/name
  (spec/nilable :sd.online.tournament/name))


(spec/def :sd.online.follow/url
  (spec/nilable
    (spec/and string? not-empty)))


(spec/def :sd.online/follow
  (spec/keys :opt-un [:sd.online.follow/show?]))


(spec/def :sd.online.follow/status-sub
  (spec/keys :opt-un [:sd.online.follow/show?
                      :sd.online.follow/name
                      :sd.online.follow/url]))


(spec/def :sd.online/online
  (spec/keys :opt-un [:sd.online/follow
                      :sd.notification/notification
                      :sd.online/tournaments
                      :sd.online/user]))


(def domain
  "eauc.eu.auth0.com")


(def client-id
  (if debug?
    "CKGG9bWf1UJvwTrU0Ya8V8tUCN7vK27C"
    "vBhy4C4dLSkuoTbmldLYNxULGTyz6swK"))


(def api-url
  (if debug?
    (str "http://" (.-hostname js/location) ":4001")
    "https://steamdating-data.herokuapp.com"))


(defn load-tournament-request
  [link confirm?]
  {:method :get
   :uri (str api-url link)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:sd.online.tournament/load-success confirm?]
   :on-failure [:sd.toaster/set
                {:type :error
                 :message "Failed to load tournament"}]})


(defn load-tournaments-request
  [{:keys [token]}]
  {:method :get
   :uri (str api-url "/tournaments/mine")
   :headers {"Authorization" (str "Bearer " token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:sd.online.tournaments/load-success]
   :on-failure [:sd.online.tournaments/load-error]})


(defn tournament-match-pattern?
  [tournament pattern]
  (some?
    (first
      (filter
        (fn [[k v]]
          (re-find pattern (str v)))
        tournament))))


(defn tournaments-filter-with
  [pattern tournaments]
  (vec (filter #(tournament-match-pattern? % pattern) tournaments)))


(defn tournaments-sort-with
  [{:keys [by reverse?]} tournaments]
  (as-> tournaments $
    (sort-by (juxt by :date) $)
    (cond-> $ reverse? (reverse))
    (vec $)))


(defn upload-tournament-request
  [token online tournament]
  (let [link (get-in tournament [:online :link])
        update? (some? link)
        data (-> (get tournament :online)
                 (merge online)
                 (select-keys [:name :date])
                 (assoc :tournament (dissoc tournament :online)))]
    {:method (if update? :put :post)
     :uri (str api-url (if update? link "/tournaments/mine"))
     :headers {"Authorization" (str "Bearer " token)}
     :format (ajax/json-request-format)
     :params data
     :response-format (ajax/json-response-format {:keywords? true})
     :on-success [:sd.online.tournament/upload-success]
     :on-failure [:sd.toaster/set
                  {:type :error
                   :message "Failed to upload current tournament"}]}))
