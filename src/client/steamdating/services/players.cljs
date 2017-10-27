(ns steamdating.services.players
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.models.player :as player]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.forms]
            [steamdating.services.routes]))


(db/reg-event-fx
  :sd.players/start-create
  (fn start-create
    []
    {:dispatch-n [[:sd.forms/reset :player {}]
                  [:sd.routes/navigate "/players/create"]]}))


;; (db/reg-event-fx
;;   :sd.players/start-edit
;;   (fn start-edit
;;     [_ [player]]
;;     {:dispatch-n [[:sd.forms/reset :player player]
;;                   [:sd.routes/navigate "/players/edit"]]}))


;; (db/reg-event-fx
;;   :sd.players/start-edit-name
;;   (fn start-edit
;;     [{:keys [db]} [name]]
;;     (let [player (first (filter #(= name (:name %))
;;                                 (get-in db [:tournament :players])))]
;;       (when player
;;         {:dispatch [:sd.players/start-edit player]}))))


(db/reg-event-fx
  :sd.players.edit/create
  [(re-frame/path [:forms :player :edit])]
  (fn edit-create
    [{:keys [db]}]
    {:dispatch-n [[:sd.players/create db]
                  [:sd.routes/back]]}))


;; (db/reg-event-fx
;;   :sd.players/update-current-edit
;;   [(re-frame/path [:forms :player])]
;;   (fn create-current-edit
;;     [{:keys [db]}]
;;     {:dispatch-n [[:sd.players/edit db]
;;                   [:sd.rounds/rename-player db]
;;                   [:sd.routes/back]]}))


(db/reg-event-fx
  :sd.players/create
  (fn create
    [{:keys [db]} [data]]
    (let [factions (:factions db)]
      {:db (update-in db [:tournament :players]
                      player/add (player/->player data factions))})))


;; (db/reg-event-fx
;;   :sd.players/edit
;;   [(re-frame/path [:tournament :players])]
;;   (fn edit
;;     [{:keys [db]} [form]]
;;     {:db (player/edit db form)}))


;; (db/reg-event-fx
;;   :sd.players/delete-current-edit
;;   (fn delete-current-edit
;;     [{:keys [db]} _]
;;     (let [player (get-in db [:forms :player :base])]
;;       {:db (update-in db [:tournament :players] player/delete player)
;;        :sd.routes/back nil})))


;; (db/reg-event-fx
;;   :sd.players/toggle-drop
;;   (fn toggle-drop
;;     [{:keys [db]} [p]]
;;     (let [n-rounds (count (get-in db [:tournament :rounds]))]
;;       {:db (update-in db [:tournament :players]
;;                       player/edit {:base p
;;                                    :edit (player/toggle-drop p n-rounds)})})))


;; (db/reg-event-fx
;;   :sd.players/import-t3
;;   (fn import-t3
;;     [{:keys [db]} [file]]
;;     {:sd.file/open
;;      {:file file
;;       :parse-fn (partial player/parse-t3-csv (faction/t3-factions (:factions db)))
;;       :on-success [:sd.players/import-t3-success]
;;       :on-failure [:sd.toaster/set
;;                    {:type :error
;;                     :message "Failed to open T3 CSV file"}]}}))


;; (db/reg-event-fx
;;   :sd.players/import-t3-success
;;   [(re-frame/path [:tournament :players])]
;;   (fn import-t3-success
;;     [{:keys [db]} [players]]
;;     (let [valid-players (filter #(spec/valid? :sd.player/player %) players)
;;           new-db (reduce #(player/add %1 %2) db valid-players)
;;           n-new-players (- (count new-db) (count db))]
;;       {:db new-db
;;        :dispatch [:sd.toaster/set
;;                   {:type :success
;;                    :message (str "Imported " n-new-players " players from T3 CSV file")}]})))


;; (db/reg-event-fx
;;   :sd.players/import-cc
;;   (fn import-cc
;;     [_ [file]]
;;     {:sd.file/open
;;      {:file file
;;       :on-success [:sd.players/import-cc-success]
;;       :on-failure [:sd.toaster/set
;;                    {:type :error
;;                     :message "Failed to open Conflict Chamber JSON file"}]}}))


;; (db/reg-event-fx
;;   :sd.players/import-cc-success
;;   (fn import-t3-success
;;     [{:keys [db]} [data]]
;;     (let [cc-factions (faction/cc-factions (:factions db))
;;           players (get-in db [:tournament :players])
;;           new-players (->> (player/convert-cc-json cc-factions data)
;;                            (filter #(spec/valid? :sd.player/player %))
;;                            (reduce #(player/add %1 %2) players))
;;           n-new-players (- (count new-players) (count players))]
;;       {:db (assoc-in db [:tournament :players] new-players)
;;        :dispatch [:sd.toaster/set
;;                   {:type :success
;;                    :message (str "Imported " n-new-players " players from T3 CSV file")}]})))


(defn players-sub
  [db]
  {:pre [(spec/valid? :sd.db/db db)]
   :post [(spec/valid? :sd.player/players %)]}
  (get-in db [:tournament :players]))

(re-frame/reg-sub
  :sd.players/players
  players-sub)


;; (re-frame/reg-sub
;;   :sd.players/exist?
;;   :<- [:sd.players/players]
;;   (fn players-names-sub
;;     [players _]
;;     (not-empty players)))


(defn players-names-sub
  [players]
  {:pre [(spec/valid? :sd.player/players players)]
   :post [(spec/valid? :sd.player/names %)]}
  (player/names players))

(re-frame/reg-sub
  :sd.players/names
  :<- [:sd.players/players]
  players-names-sub)


;; (re-frame/reg-sub
;;   :sd.players/factions
;;   :<- [:sd.players/players]
;;   (fn players-factions-sub
;;     [players _]
;;     (player/factions players)))


;; (re-frame/reg-sub
;;   :sd.players/origins
;;   :<- [:sd.players/players]
;;   (fn players-factions-sub
;;     [players _]
;;     (player/origins players)))


;; (re-frame/reg-sub
;;   :sd.players/lists
;;   :<- [:sd.players/players]
;;   (fn players-factions-sub
;;     [players _]
;;     (player/lists players)))


;; (re-frame/reg-sub
;;   :sd.players/sorted
;;   :<- [:sd.players/players]
;;   :<- [:sd.sorts/sort :player {:by :name}]
;;   (fn players-sorted-sub
;;     [[players sort] _]
;;     (player/sort-with players sort)))


;; (re-frame/reg-sub
;;   :sd.players/list
;;   (fn players-lists-inputs
;;     [[_ filter] _]
;;     [(re-frame/subscribe [:sd.players/sorted])
;;      (re-frame/subscribe [:sd.filters/pattern filter])])
;;   (fn players-list-sub
;;     [[players pattern] _]
;;     (player/filter-with players pattern)))


(defn edit-casters-sub
  [[factions {:keys [edit] :as form}]]
  {:pre [(spec/valid? :sd.faction/factions factions)
         (spec/valid? :sd.form/form form)]
   :post [(spec/valid? :sd.faction/casters %)]}
  (let [edit-faction (:faction edit)]
    (faction/casters factions edit-faction)))

(re-frame/reg-sub
  :sd.players.edit/casters
  :<- [:sd.factions/factions]
  :<- [:sd.forms/form :player]
  edit-casters-sub)


(defn edit-sub
  [[{:as form-state
     {base-name :name} :base
     {edit-name :name} :edit} names factions casters]]
  {:pre [(spec/valid? :sd.form/form form-state)
         (spec/valid? :sd.player/names names)
         (spec/valid? :sd.faction/names factions)
         (spec/valid? :sd.faction/casters casters)]
   :post [(spec/valid? :sd.player/form %)]}
  (let [restricted-names (disj names base-name)]
    (-> form-state
        (assoc :factions factions :casters casters)
        (cond-> (restricted-names edit-name)
          (assoc-in [:error :name] "Name already exists")))))

(re-frame/reg-sub
  :sd.players/edit
  :<- [:sd.forms/validate :player player/validate]
  :<- [:sd.players/names]
  :<- [:sd.factions/names]
  :<- [:sd.players.edit/casters]
  edit-sub)
