(ns steamdating.pages.home
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.services.db :as db]
            [clojure.string :as str]))


(defroute root "/" {}
  (println "route home")
  (re-frame/dispatch [:steamdating.routes/page :home]))


(defroute home "/home" {}
  (println "route home")
  (re-frame/dispatch [:steamdating.routes/page :home]))


(db/reg-event-fx
  ::test-prompt
  (fn test-prompt
    [_ [message value]]
    (println "test-prompt" message value)
    {:dispatch [:steamdating.toaster/set
                {:type :info
                 :message (str/join " : " (remove nil? [message value]))}]}))


(defmethod page-root/render :home
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:steamdating.toaster/set
                    {:type :success
                     :message "Ouuuuups1!"}])}
     "Test Toaster"]
    [menu-item
     {:on-click #(do (re-frame/dispatch
                       [:steamdating.toaster/set
                        {:type :error
                         :message "Ouuuuups1!"}])
                     (re-frame/dispatch
                       [:steamdating.toaster/set
                        {:type :info
                         :message "Ouuuuups2!"}])
                     (re-frame/dispatch
                       [:steamdating.toaster/set
                        {:type :success
                         :message "Ouuuuups3!"}])
                     (re-frame/dispatch
                       [:steamdating.toaster/set
                        {:type :warning
                         :message "Ouuuuups4!"}])
                     (re-frame/dispatch
                       [:steamdating.toaster/set
                        {:type :error
                         :message "Ouuuuups5!"}]))}
     "Test Toaster x5"]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:steamdating.prompt/set
                    {:type :alert
                     :message "This is an alert"
                     :on-validate [::test-prompt "alert-ok"]}])}
     "Test Alert"]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:steamdating.prompt/set
                    {:type :confirm
                     :message "This is a confirm"
                     :on-validate [::test-prompt "confirm-ok"]
                     :on-cancel [::test-prompt "confirm-cancel"]}])}
     "Test Confirm"]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:steamdating.prompt/set
                    {:type :prompt
                     :message "This is a prompt:"
                     :value 42
                     :on-validate [::test-prompt "prompt-ok"]
                     :on-cancel [::test-prompt "prompt-cancel"]}])}
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
