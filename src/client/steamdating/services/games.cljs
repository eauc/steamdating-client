(ns steamdating.services.games
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.game :as game]
            [steamdating.models.player :as player]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(db/reg-event-fx
  :sd.games.edit/start
  [(re-frame/path [:tournament :rounds])]
  (fn edit-start
    [{rounds :db} [n-round game]]
    (let [names (game/player-names game)
          games (get-in rounds [n-round :games])
          game (game/find-by-names names games)]
      (when (some? game)
        {:dispatch-n [[:sd.forms/reset :game (assoc game :n-round n-round)]
                      [:sd.routes/navigate "/games/edit"]]}))))


(db/reg-event-fx
  :sd.games.edit/toggle-win-loss
  [(re-frame/path [:forms :game :edit])]
  (fn edit-toggle-win-loss
    [{game :db} [p-key]]
    {:db (game/toggle-win-loss game p-key)}))


(db/reg-event-fx
  :sd.games.edit/random-score
  (fn edit-random-score
    [{:keys [db]} _]
    (let [lists (player/lists (get-in db [:tournament :players]))]
      {:db (update-in db [:forms :game :edit]
                      game/random-score lists)})))


(db/reg-event-fx
  :sd.games.edit/save
  (fn update-current-edit
    [{:keys [db]} _]
    (let [names (game/player-names (get-in db [:forms :game :base]))
          n-round (get-in db [:forms :game :base :n-round])
          edit (get-in db [:forms :game :edit])]
      {:db (update-in db [:tournament :rounds n-round :games]
                      #(game/update-by-names names (dissoc edit :n-round) %))
       :dispatch [:sd.routes/back]})))


(defn edit-sub
  [[state names lists]]
  {:pre [(debug/spec-valid? :sd.form/validated state)
         (debug/spec-valid? :sd.player/names names)
         (debug/spec-valid? :sd.player.lists/sub lists)]
   :post [(debug/spec-valid? :sd.game/edit %)]}
  (assoc state
         :options (into {} (map vector names names))
         :lists lists))

(re-frame/reg-sub
  :sd.games/edit
  :<- [:sd.forms/validate :game game/validate]
  :<- [:sd.players/names]
  :<- [:sd.players/lists]
  edit-sub)
