(ns steamdating.services.file
  (:require [re-frame.core :as re-frame]
            [steamdating.services.debug :as debug]))


(re-frame/reg-fx
  :steamdating.file/open
  (fn file-open
    [{:keys [file on-success on-failure]}]
    (let [on-load #(let [result (-> % .-target .-result)]
                     (try
                       (-> result
                           (js/JSON.parse)
                           (js->clj :keywordize-keys true)
                           (->> (conj on-success))
                           (re-frame/dispatch))
                       (catch js/Object e
                         (debug/spy "Parse file error" (.-message e))
                         (re-frame/dispatch on-failure))))
          on-error #(do
                      (debug/spy "Load file error" %)
                      (re-frame/dispatch on-failure))]
      (doto (js/FileReader.)
        (aset "onload" on-load)
        (aset "onerror" on-error)
        (aset "onabort" on-error)
        (.readAsText file)))))
