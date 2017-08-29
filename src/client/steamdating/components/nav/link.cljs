(ns steamdating.components.nav.link)


(defn link
	[{:keys [active current-hash path on-click]} & children]
	(let [hash (str "#" path)
				active? (-> current-hash (.startsWith (or active hash)))]
		[:a.sd-NavItem {:href hash
										:on-click on-click
										:class (when active? "sd-NavItem-active")}
		 children]))
