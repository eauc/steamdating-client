(ns steamdating.pages.home
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]))


(defroute home "/home" {}
  (re-frame/dispatch [:sd.routes/page :home]))


(defmethod page-content :home
  []
  [:div.sd-page-home
   [:h4 "Home"]
   [:p "Welcome Home ! 1"]
   [:p "Welcome Home ! 2"]
   [:p "Welcome Home ! 3"]
   [:p "Welcome Home ! 4"]
   [:p "Welcome Home ! 5"]
   [:p "Welcome Home ! 6"]
   [:p "Welcome Home ! 7"]
   [:p "Welcome Home ! 8"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]])


(defmethod page-menu-items :home
  []
  (list
    [:button.sd-page-menu-item
     {:key :toater-success
      :on-click #(re-frame/dispatch
                   [:sd.toaster/set
                    {:type :success
                     :message "Ouuuuups1!"}])}
     "Test Toaster Success"]
    [:button.sd-page-menu-item
     {:key :toater-info
      :on-click #(re-frame/dispatch
                   [:sd.toaster/set
                    {:type :info
                     :message "Ouuuuups2!"}])}
     "Test Toaster Info"]
    [:button.sd-page-menu-item
     {:key :toater-warn
      :on-click #(re-frame/dispatch
                   [:sd.toaster/set
                    {:type :warn
                     :message "Ouuuuups3!"}])}
     "Test Toaster Warn"]
    [:button.sd-page-menu-item
     {:key :toater-error
      :on-click #(re-frame/dispatch
                   [:sd.toaster/set
                    {:type :error
                     :message "Ouuuuups4!"}])}
     "Test Toaster Error"]
    [:button.sd-page-menu-item
     {:key :prompt-alert
      :on-click #(re-frame/dispatch
                   [:sd.prompt/set
                    {:type :alert
                     :message "This is an alert"
                     :on-validate [::test-prompt "alert-ok"]}])}
     "Test Alert"]
    [:button.sd-page-menu-item
     {:key :prompt-confirm
      :on-click #(re-frame/dispatch
                   [:sd.prompt/set
                    {:type :confirm
                     :message "This is a confirm"
                     :on-validate [::test-prompt "confirm-ok"]
                     :on-cancel [::test-prompt "confirm-cancel"]}])}
     "Test Confirm"]
    [:button.sd-page-menu-item
     {:key :prompt-prompt
      :on-click #(re-frame/dispatch
                   [:sd.prompt/set
                    {:type :prompt
                     :message "This is a prompt:"
                     :value 42
                     :on-validate [::test-prompt "prompt-ok"]
                     :on-cancel [::test-prompt "prompt-cancel"]}])}
     "Test Prompt"]))


(db/reg-event-fx
  ::test-prompt
  (fn test-prompt
    [_ [msg value]]
    {:dispatch [:sd.toaster/set {:type :info
                                 :message (str msg (when (some? value) (str " : " value)))}]}))
