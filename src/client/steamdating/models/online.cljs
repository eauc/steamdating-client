(ns steamdating.models.online
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as spec]
            [steamdating.services.debug :refer [debug?]]))


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


(def domain
  "eauc.eu.auth0.com")


(def client-id
  (if debug?
    "CKGG9bWf1UJvwTrU0Ya8V8tUCN7vK27C"
    "vBhy4C4dLSkuoTbmldLYNxULGTyz6swK"))


(def api-url
  (if debug?
    "http://localhost:4001"
    "https://steamdating-data.herokuapp.com"))


(defn load-tournament-request
  [token link confirm?]
  {:method :get
   :uri (str api-url link)
   :headers {"Authorization" (str "Bearer " token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:steamdating.online/load-tournament-success confirm?]
   :on-failure [:steamdating.online/error-logout
                "Failed to load online tournament"]})


(defn load-tournaments-request
  [token]
  {:method :get
   :uri (str api-url "/tournaments/mine")
   :headers {"Authorization" (str "Bearer " token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:steamdating.online/load-tournaments-success]
   :on-failure [:steamdating.online/load-tournaments-error]})


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
     :on-success [:steamdating.online/upload-current-success]
     :on-failure [:steamdating.online/error-logout
                  "Failed to upload current tournament"]}))
