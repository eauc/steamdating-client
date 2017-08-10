(ns steamdating.components.page.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :steamdating.routes/page
  (fn page-sub
    [db _]
    (:page db)))
