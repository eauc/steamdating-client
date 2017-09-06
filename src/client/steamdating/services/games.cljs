(ns steamdating.services.games
	(:require [clairvoyant.core :refer-macros [trace-forms]]
						[re-frame.core :as re-frame]
						[re-frame-tracer.core :refer [tracer]]
						[steamdating.models.game :as game]
						[steamdating.services.db :as db]))

(trace-forms
	{:tracer (tracer :color "green")}

	(db/reg-event-fx
		:steamdating.games/start-edit
		[(re-frame/path [:tournament :rounds])]
		(fn start-edit
			[{rounds :db} [n-round game]]
			(let [names (game/player-names game)
						games (get-in rounds [n-round :games])
						game (game/find-by-names names games)]
				(when (some? game)
					{:dispatch-n [[:steamdating.forms/reset :game (assoc game :n-round n-round)]
												[:steamdating.routes/navigate "/games/edit"]]}))))


  (db/reg-event-fx
		:steamdating.games/edit-toggle-win-loss
		[(re-frame/path [:forms :game :edit])]
		(fn edit-toggle-win-loss
			[{game :db} [p-key]]
			{:db (game/toggle-win-loss game p-key)}))


  (db/reg-event-fx
		:steamdating.games/update-current-edit
		(fn update-current-edit
			[{:keys [db]} _]
      (let [names (game/player-names (get-in db [:forms :game :base]))
            n-round (get-in db [:forms :game :base :n-round])
            edit (get-in db [:forms :game :edit])]
        {:db (update-in db [:tournament :rounds n-round :games]
                        #(game/update-by-names names (dissoc edit :n-round) %))
         :dispatch [:steamdating.routes/back]})))


  (re-frame/reg-sub
    :steamdating.games/edit
    :<- [:steamdating.forms/validate :game game/validate]
    :<- [:steamdating.players/names]
    :<- [:steamdating.players/lists]
    (fn edit-sub
      [[form-state names lists] _]
      (-> form-state
          (assoc :players (into {} (map vector names names)))
          (assoc :lists (into {} (map (fn [[name ls]]
                                        [name (into {} (map vector ls ls))])
                                      lists))))))

  )
