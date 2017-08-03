(ns steamdating.components.toaster.sub
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
  :toaster
  (fn toaster-sub
    [db _]
    (:toaster db)))
