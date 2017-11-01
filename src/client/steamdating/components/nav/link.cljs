(ns steamdating.components.nav.link)


(defn nav-link
  [{:keys [active-on hash target]} & children]
  (let [active? (-> hash (.startsWith (or active-on target)))]
    [:a.sd-nav-menu-item {:href target
                          :class (when active? "active")}
     children]))
