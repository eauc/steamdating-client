(ns steamdating.styles.1-tools.scrollable-table)


(def scrollable-table
	[:& {:overflow :hidden
			 :position :relative}

	 [:&-scrollable {:overflow-x :auto}

    [:.sd-table-caption {:opacity 0
                         :pointer-events :none}]]

	 [:&-overlay {:left 0
								:pointer-events :none
								:position :absolute
								:top 0}

		[:.sd-table-caption {:background-color :white
                         :pointer-events :all}]

		[:th :td {:background-color :white
              :opacity 0}

		 [:&.fixed {:opacity 1
                :pointer-events :all}]]]

	 [:.sd-table
		[:tbody
		 [:tr
			[:&:hover {:background-color :inherit}]]]]])
