(ns steamdating.components.online.follow
  (:require [cljsjs.kjua]
            [re-frame.core :as re-frame]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.services.debug :as debug]))


(defn render-qr-code
  [url ref]
  (when ref
    (aset ref "innerHTML" "")
    (let [rect (.getBoundingClientRect ref)
          width (aget rect "width")
          qr-element (js/kjua (clj->js {:text url
                                        :ecLevel "M"
                                        :size (- width 10)}))]
      ;; (js/console.log
      ;;   "render-qr-code"
      ;;   ref rect qr-element)
      (.appendChild ref qr-element))))


(defn online-follow
  []
  (let [{:keys [show? url name]} @(re-frame/subscribe [:sd.online.follow/status])]
    [:div.sd-online-follow
     {:class (when show? "show")
      :on-click #(re-frame/dispatch [:sd.online.follow/hide])}
     [:h4 (str "Follow " name)]
     [:p
      [:a.sd-online-follow-link
       {:href url} url]]
     [:div.sd-online-follow-qrcode
      {:ref #(render-qr-code url (when show? %))}]
     [button
      {:icon"x"
       :label "Close"}]]))
