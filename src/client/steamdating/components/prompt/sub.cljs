(ns steamdating.components.prompt.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :steamdating.prompt/prompt
  (fn prompt-sub
    [db _]
    (:prompt db)))
