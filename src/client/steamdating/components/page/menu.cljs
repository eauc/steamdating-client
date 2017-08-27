(ns steamdating.components.page.menu
  (:require [reagent.core :as reagent]
            [steamdating.components.generics.icon :refer [icon]]))

(defn menu-item
  [{:keys [href on-click]} & children]
  (apply conj
         [:a.sd-PageMenuItem
          {:href (or href "#")
           :on-click (fn [event]
                       (.preventDefault event)
                       (on-click))}]
         children))


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
