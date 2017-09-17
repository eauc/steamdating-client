(ns steamdating.services.players
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.models.faction :as faction]
            [steamdating.models.player :as player]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(trace-forms
  {:tracer (tracer :color "green")}

  (db/reg-event-fx
    :steamdating.players/start-create
    (fn start-create
      []
      {:dispatch-n [[:steamdating.forms/reset :player {}]
                    [:steamdating.routes/navigate "/players/create"]]}))


  (db/reg-event-fx
    :steamdating.players/start-edit
    (fn start-edit
      [_ [player]]
      {:dispatch-n [[:steamdating.forms/reset :player player]
                    [:steamdating.routes/navigate "/players/edit"]]}))


  (db/reg-event-fx
    :steamdating.players/start-edit-name
    (fn start-edit
      [{:keys [db]} [name]]
      (let [player (first (filter #(= name (:name %))
                                  (get-in db [:tournament :players])))]
        (when player
          {:dispatch [:steamdating.players/start-edit player]}))))


  (db/reg-event-fx
    :steamdating.players/create-current-edit
    [(re-frame/path [:forms :player :edit])]
    (fn create-current-edit
      [{:keys [db]}]
      {:dispatch-n [[:steamdating.players/create db]
                    [:steamdating.routes/back]]}))


  (db/reg-event-fx
    :steamdating.players/update-current-edit
    [(re-frame/path [:forms :player])]
    (fn create-current-edit
      [{:keys [db]}]
      {:dispatch-n [[:steamdating.players/edit db]
                    [:steamdating.rounds/rename-player db]
                    [:steamdating.routes/back]]}))


  (db/reg-event-fx
    :steamdating.players/create
    [(re-frame/path [:tournament :players])]
    (fn create
      [{:keys [db]} [player]]
      {:db (player/add db player)}))


  (db/reg-event-fx
    :steamdating.players/edit
    [(re-frame/path [:tournament :players])]
    (fn edit
      [{:keys [db]} [form]]
      {:db (player/edit db form)}))


  (db/reg-event-fx
    :steamdating.players/delete-current-edit
    (fn delete-current-edit
      [{:keys [db]} _]
      (let [player (get-in db [:forms :player :base])]
        {:db (update-in db [:tournament :players] player/delete player)
         :steamdating.routes/back nil})))


  (db/reg-event-fx
    :steamdating.players/toggle-drop
    (fn toggle-drop
      [{:keys [db]} [p]]
      (let [n-rounds (count (get-in db [:tournament :rounds]))]
        {:db (update-in db [:tournament :players]
                        player/edit {:base p
                                     :edit (player/toggle-drop p n-rounds)})})))


  (db/reg-event-fx
    :steamdating.players/import-t3
    (fn import-t3
      [{:keys [db]} [file]]
      {:steamdating.file/open
       {:file file
        :parse-fn (partial player/parse-t3-csv (faction/t3-factions (:factions db)))
        :on-success [:steamdating.players/import-t3-success]
        :on-failure [:steamdating.toaster/set
                     {:type :error
                      :message "Failed to open T3 CSV file"}]}}))


  (db/reg-event-fx
    :steamdating.players/import-t3-success
    [(re-frame/path [:tournament :players])]
    (fn import-t3-success
      [{:keys [db]} [players]]
      (let [valid-players (filter #(spec/valid? :steamdating.player/player %) players)
            new-db (reduce #(player/add %1 %2) db valid-players)
            n-new-players (- (count new-db) (count db))]
        {:db new-db
         :dispatch [:steamdating.toaster/set
                    {:type :success
                     :message (str "Imported " n-new-players " players from T3 CSV file")}]})))


  (db/reg-event-fx
    :steamdating.players/import-cc
    (fn import-cc
      [_ [file]]
      {:steamdating.file/open
       {:file file
        :on-success [:steamdating.players/import-cc-success]
        :on-failure [:steamdating.toaster/set
                     {:type :error
                      :message "Failed to open Conflict Chamber JSON file"}]}}))


  (db/reg-event-fx
    :steamdating.players/import-cc-success
    (fn import-t3-success
      [{:keys [db]} [data]]
      (let [cc-factions (faction/cc-factions (:factions db))
            players (get-in db [:tournament :players])
            new-players (->> (player/convert-cc-json cc-factions data)
                             (filter #(spec/valid? :steamdating.player/player %))
                             (reduce #(player/add %1 %2) players))
            n-new-players (- (count new-players) (count players))]
        {:db (assoc-in db [:tournament :players] new-players)
         :dispatch [:steamdating.toaster/set
                    {:type :success
                     :message (str "Imported " n-new-players " players from T3 CSV file")}]})))


  (re-frame/reg-sub
    :steamdating.players/players
    (fn players-sub
      [db _]
      (get-in db [:tournament :players])))


  (re-frame/reg-sub
    :steamdating.players/names
    :<- [:steamdating.players/players]
    (fn players-names-sub
      [players _]
      (player/names players)))


  (re-frame/reg-sub
    :steamdating.players/factions
    :<- [:steamdating.players/players]
    (fn players-factions-sub
      [players _]
      (player/factions players)))


  (re-frame/reg-sub
    :steamdating.players/origins
    :<- [:steamdating.players/players]
    (fn players-factions-sub
      [players _]
      (player/origins players)))


  (re-frame/reg-sub
    :steamdating.players/lists
    :<- [:steamdating.players/players]
    (fn players-factions-sub
      [players _]
      (player/lists players)))


  (re-frame/reg-sub
    :steamdating.players/sorted
    :<- [:steamdating.players/players]
    :<- [:steamdating.sorts/sort :player {:by :name}]
    (fn players-sorted-sub
      [[players sort] _]
      (player/sort-with players sort)))


  (re-frame/reg-sub
    :steamdating.players/list
    :<- [:steamdating.players/sorted]
    :<- [:steamdating.filters/pattern :player]
    (fn players-list-sub
      [[players pattern] _]
      (player/filter-with players pattern)))


  (re-frame/reg-sub
    :steamdating.players/edit-casters
    (fn edit-casters-input
      []
      [(re-frame/subscribe [:steamdating.factions/factions])
       (re-frame/subscribe [:steamdating.forms/form :player])])
    (fn edit-casters-sub
      [[factions {:keys [edit] :as form}] _]
      (let [edit-faction (:faction edit)]
        (faction/casters factions edit-faction))))


  (re-frame/reg-sub
    :steamdating.players/edit
    (fn edit-input
      []
      [(re-frame/subscribe [:steamdating.players/names])
       (re-frame/subscribe [:steamdating.forms/validate :player player/validate])])
    (fn edit-sub
      [[names {{base-name :name} :base
               {edit-name :name} :edit
               :as form-state}] _]
      (let [restricted-names (disj names base-name)]
        (cond-> form-state
          (restricted-names edit-name)
          (assoc-in [:error :name] "Name already exists")))))

  )
