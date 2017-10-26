(ns steamdating.services.file
  (:require [re-frame.core :as re-frame]
            [steamdating.services.debug :refer [log]]))


(defn parse-json
  [result]
  (-> result
      (js/JSON.parse)
      (js->clj :keywordize-keys true)))


(re-frame/reg-fx
  :sd.file/open
  (fn file-open
    [{:keys [file on-success on-failure parse-fn]
      :or {parse-fn parse-json}}]
    (let [on-load #(let [result (-> % .-target .-result)]
                     (try
                       (->> result
                            (parse-fn)
                            (conj on-success)
                            (re-frame/dispatch))
                       (catch js/Object e
                         (log "Parse file error" (.-message e))
                         (re-frame/dispatch on-failure))))
          on-error #(do
                      (log "Load file error" %)
                      (re-frame/dispatch on-failure))]
      (doto (js/FileReader.)
        (aset "onload" on-load)
        (aset "onerror" on-error)
        (aset "onabort" on-error)
        (.readAsText file)))))
