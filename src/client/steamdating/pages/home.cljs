(ns steamdating.pages.home
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.services.db :as db]))


(defroute home "/" {}
  (println "route home")
  (re-frame/dispatch [:page :home]))


(db/reg-event-fx
  :test-prompt
  (fn test-prompt
    [_ [message value]]
    (println "test-prompt" message value)
    {}))


(defmethod page-root/render :home
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:prompt-set {:type :alert
                                 :message "This is an alert"
                                 :on-validate [:test-prompt "alert-ok"]}])}
     "Test Alert"]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:prompt-set {:type :confirm
                                 :message "This is a confirm"
                                 :on-validate [:test-prompt "confirm-ok"]
                                 :on-cancel [:test-prompt "confirm-cancel"]}])}
     "Test Confirm"]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:prompt-set {:type :prompt
                                 :message "This is a prompt:"
                                 :value 42
                                 :on-validate [:test-prompt "prompt-ok"]
                                 :on-cancel [:test-prompt "prompt-cancel"]}])}
     "Test Prompt"]]
   [content
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]
    [:p "Welcome Home!"]]])
