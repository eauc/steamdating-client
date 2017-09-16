(ns steamdating.styles.4-objects.file-save
  (:require [garden.def :as gdef]
            [steamdating.styles.1-tools.button :refer [button]]))


(gdef/defstyles file-save
  [:&
   (button [:&-FileSaveButton])])
