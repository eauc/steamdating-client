(defproject steamdating "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :clean-targets ^{:protect false} ["node_modules"
                                    "resources/public/css"
                                    "resources/public/js"
                                    "target"]
  :dependencies [[org.clojure/clojure "1.9.0-alpha4"]
                 [org.clojure/clojurescript "1.9.854"]
                 [devcards "0.2.3"]
                 [garden "1.3.2"]
                 [re-frame "0.9.4" :exclusions [reagent]]
                 [secretary "1.2.3"]
                 [expound "0.1.2"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [pjstadig/humane-test-output "0.8.2"]
                 [reagent "0.7.0"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.12" :exclusions [org.clojure/clojure]]
            [lein-garden "0.3.0" :exclusions [org.clojure/clojure org.apache.commons/commons-compress]]
            [lein-pprint "1.1.2"]]
  :source-paths []
  :hooks [leiningen.cljsbuild]
  :prep-tasks [["garden" "once"]]
  :cljsbuild
  {:builds
   {:client {:source-paths ["src/client"]
             :compiler {:main "steamdating.core"
                        :optimizations :none
                        :output-to "resources/public/js/client.js"
                        :npm-deps {:feather-icons "3.2.2"}
                        :install-deps true}}
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
  :figwheel {:css-dirs ["resources/public/css"]
             :validate-config false}
  :garden
  {:builds
   [{:id :screen
     :source-paths ["src/client"]
     :stylesheet steamdating.styles/screen
     :compiler {:output-to "resources/public/css/screen.css"
                :pretty-print? true}}]}
  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.4"]
                   [figwheel-sidecar "0.5.12" :exclusions [commons-codec
                                                           org.clojure/tools.nrepl
                                                           org.clojure/core.async]]
                   [com.cemerick/piggieback "0.2.2"]]
    :cljsbuild
    {:builds
     {:client {:figwheel {:on-jsload "steamdating.core/mount-root"}
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
     {:client {:compiler {:closure-defines {"goog.DEBUG" true}
                          :optimizations :advanced}}}}}
   :repl [:dev]})
