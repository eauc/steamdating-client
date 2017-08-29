(ns steamdating.components.page.menu
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [steamdating.components.generics.icon :refer [icon]]))

(defn menu-item
  [{:keys [active href on-click]} & children]
  (let [current-hash @(re-frame/subscribe [:steamdating.routes/hash])
        active? (or (.startsWith current-hash active)
                    (= current-hash href))]
    (apply conj
           [:a.sd-PageMenuItem
            {:class (when active? "active")
             :href (or href "#")
             :on-click (fn [event]
                         (when on-click
                           (.preventDefault event)
                           (on-click)))}]
           children)))


(defn menu-toggle
  [{:keys [on-toggle show]}]
  [:button.sd-PageMenuToggle
   {:class (when show "sd-PageMenuToggle-hide")
    :on-click on-toggle}
   [icon "chevrons-left"]])


(defn menu
  []
  (let [show (reagent/atom false)
        do-hide #(reset! show false)
        toggle-show #(swap! show not)]
    (fn menu-component
      [& children]
      [:div.sd-PageMenu
       {:class (when @show "sd-PageMenu-show")}
       (apply conj
              [:div.sd-PageMenu-insider
               {:on-click do-hide}]
              children)
       [menu-toggle
        {:on-toggle toggle-show
         :show @show}]])))
