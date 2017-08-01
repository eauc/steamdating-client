(defproject steamdating "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :main steamdating.server.core
  :clean-targets ^{:protect false} ["node_modules" "target"]
  :dependencies [[org.clojure/clojure "1.9.0-alpha4"]
                 [org.clojure/clojurescript "1.9.854"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-pprint "1.1.2"]]
  :source-paths ["src"]
  :hooks [leiningen.cljsbuild]
  :cljsbuild
  {:builds
   {:server {:source-paths ["src/server"]
             :compiler {:main "steamdating.core"
                        :target :nodejs
                        :optimizations :none
                        :pretty-print true
                        :output-to "target/js/server.js"
                        :output-dir "target/js/server"
                        :npm-deps {:compression "1.7.0"
                                   :express "4.15.3"}
                        :install-deps true}}}})
