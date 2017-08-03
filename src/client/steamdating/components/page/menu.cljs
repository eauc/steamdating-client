(ns steamdating.components.page.menu
  (:require [reagent.core :as reagent]
            [steamdating.components.generics.icon :refer [icon]]))


(defn menu-item
  [{:keys [href on-click]} & children]
  (apply conj
         [:a.sd-PageMenuItem {:href href :on-click on-click}]
         children))


(defn menu-toggle
  [{:keys [on-toggle show]}]
  [:button.sd-PageMenuToggle {:on-click on-toggle}
   [icon {:name (if show "chevron-down" "chevron-up")}]])


(defn menu
  []
  (let [show (reagent/atom false)
        toggle-show #(swap! show not)]
    (fn menu-component
      [& children]
      (conj
        (apply conj
               [:div.sd-PageMenu {:class (when @show "sd-PageMenu-show")}]
               children)
        [menu-toggle {:on-toggle toggle-show
                      :show @show}]))))
