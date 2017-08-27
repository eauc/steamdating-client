(ns steamdating.services.players
  (:require [clairvoyant.core :refer-macros [trace-forms]]
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
    (fn create
      [{:keys [db]} [form]]
      {:db (player/edit db form)}))


  (db/reg-event-fx
    :steamdating.players/delete-current-edit
    (fn delete-current-edit
      [{:keys [db]} _]
      (let [player (get-in db [:forms :player :base])]
        {:db (update-in db [:tournament :players] player/delete player)
         :steamdating.routes/back nil})))


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
