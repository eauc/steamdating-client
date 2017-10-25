(ns steamdating.services.routes
	(:import goog.History)
	(:require [cljs.spec.alpha :as spec]
						[goog.events :as events]
						[goog.history.EventType :as EventType]
						[re-frame.core :as re-frame]
						[secretary.core :as secretary]
						[steamdating.services.db :as db]
						[steamdating.services.debug :refer [debug?]]))


(defonce history (History.))


(defn hook-browser-navigation!
	[]
	(events/listen history EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
	(.setEnabled history true))


(defn routes-init
	[]
	(secretary/set-config! :prefix "#")
	(secretary/defroute root "/" {}
		(re-frame/dispatch [:sd.routes/navigate
												(if debug? "/home" "/players")]))
  (secretary/defroute all "*" {}
		(re-frame/dispatch [:sd.routes/page :unknown]))
	(hook-browser-navigation!))


(re-frame/reg-cofx
	:sd.routes/current-hash
	(fn current-hash
		[coeffects]
		(assoc coeffects :sd.routes/current-hash (.-hash js/location))))


(db/reg-event-fx
	:sd.routes/page
	[(re-frame/path :route)
	 (re-frame/inject-cofx :sd.routes/current-hash)]
	(fn page [{:keys [db :sd.routes/current-hash]} [page params]]
		{:db (merge db {:page page
										:hash current-hash
										:params (or params {})})}))


(re-frame/reg-fx
	:sd.routes/navigate
	(fn navigate-fx
		[to]
		(.setToken history to)))


(db/reg-event-fx
	:sd.routes/navigate
	(fn navigate
		[_ [to]]
		{:sd.routes/navigate to}))


(re-frame/reg-fx
	:sd.routes/back
	(fn back-fx
		[]
		(.back js/window.history)))


(db/reg-event-fx
	:sd.routes/back
	(fn routes-back
		[_ _]
		{:sd.routes/back nil}))


(defn page-sub
	[db]
	{:pre [(spec/valid? :sd.db/db db)]
	 :post [(spec/valid? :sd.route/route %)]}
	(:route db))

(re-frame/reg-sub
	:sd.routes/route
	page-sub)


(defn hash-sub
	[route]
	{:pre [(spec/valid? :sd.route/route route)]
	 :post [(spec/valid? :sd.route/hash %)]}
	(get route :hash ""))

(re-frame/reg-sub
	:sd.routes/hash
	:<- [:sd.routes/route]
	hash-sub)
