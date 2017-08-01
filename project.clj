(defproject steamdating "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :main steamdating.server.core
  :clean-targets ^{:protect false} ["node_modules" "target" "resources/public/js"]
  :dependencies [[org.clojure/clojure "1.9.0-alpha4"]
                 [org.clojure/clojurescript "1.9.854"]
                 [devcards "0.2.3"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.12"]
            [lein-pprint "1.1.2"]]
  :source-paths ["src"]
  :hooks [leiningen.cljsbuild]
  :cljsbuild
  {:builds
   {:client {:source-paths ["src/client"]
             :compiler {:main "steamdating.core"
                        :optimizations :none
                        :output-to "resources/public/js/client.js"}}
    :test {:source-paths ["src/client" "test/client"]
           :compiler {:main "steamdating.core-test"
                      :devcards true
                      :optimizations :none
                      :pretty-print true
                      :output-to "resources/public/js/test.js"
                      :output-dir "resources/public/js/test"
                      :asset-path "js/test"}}
    :server {:source-paths ["src/server"]
             :compiler {:main "steamdating.core"
                        :target :nodejs
                        :optimizations :none
                        :pretty-print true
                        :output-to "target/js/server.js"
                        :output-dir "target/js/server"
                        :npm-deps {:compression "1.7.0"
                                   :express "4.15.3"}
                        :install-deps true}}}}
  :figwheel {:validate-config false}
  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.4"]
                   [figwheel-sidecar "0.5.12"]
                   [com.cemerick/piggieback "0.2.2"]]
    :cljsbuild
    {:builds
     {:client {:figwheel true
               :compiler {:pretty-print true
                          :output-dir "resources/public/js/client"
                          :asset-path "js/client"
                          :preloads [devtools.preload]
                          :tooling-config {:devtools/config {:features-to-install :all}}}}
      :test {:figwheel {:devcards true}
             :compiler {:preloads [devtools.preload]}}}}}
   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
   :production
   {:cljsbuild
    {:builds
     {:client {:compiler {:optimizations :advanced}}}}}
   :repl [:dev]})
