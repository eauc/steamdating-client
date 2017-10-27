(defproject steamdating "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :clean-targets ^{:protect false} ["node_modules"
                                    "resources/public/css"
                                    "resources/public/js"
                                    "target"]
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/clojurescript "1.9.908"]
                 [expound "0.3.1"]
                 [garden "1.3.3"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [phrase "0.1-alpha1"]
                 [re-frame "0.10.2" :exclusions [reagent]]
                 [reagent "0.7.0"]
                 [secretary "1.2.3"]]
  :plugins [[lein-ancient "0.6.12"]
            [lein-cljsbuild "1.1.7" :exclusions [org.clojure/clojure
                                                 org.apache.commons/commons-compress]]
            [lein-figwheel "0.5.14"]
            [lein-garden "0.3.0" :exclusions [org.clojure/clojure]]
            [lein-pprint "1.1.2"]
            [lein-shell "0.5.0"]]
  :source-paths []
  :aliases {"compile" ["do"
                       ["garden" "once"]
                       ["cljsbuild" "once"]
                       ["shell" "npm" "run" "appcache"]
                       ["shell" "npm" "run" "sw-precache"]]}
  :cljsbuild
  {:builds
   {:client {:source-paths ["src/client"]
             :compiler {:main "steamdating.core"
                        :optimizations :none
                        :output-to "resources/public/js/client.js"
                        :npm-deps {:feather-icons "3.2.2"
                                   :sw-precache "5.2.0"}
                        :install-deps true}}
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
   {:dependencies [[org.clojars.stumitchell/clairvoyant "0.2.1"]
                   [devcards "0.2.4" :exclusions [cljsjs/react
                                                  cljsjs/react-dom
                                                  commons-codec]]
                   [binaryage/devtools "0.9.7"]
                   [figwheel-sidecar "0.5.14" :exclusions [org.clojure/tools.nrepl]]
                   [com.cemerick/piggieback "0.2.2"]]
    :cljsbuild
    {:builds
     {:client {:figwheel {:on-jsload "steamdating.core/mount-root"}
               :compiler {:pretty-print true
                          :output-dir "resources/public/js/client"
                          :asset-path "js/client"
                          :closure-defines {"clairvoyant.core.devmode" true}
                          :preloads [devtools.preload]
                          :tooling-config {:devtools/config {:features-to-install :all}}}}
      :test {:figwheel {:devcards true}
             :source-paths ["src/client" "test/client"]
             :compiler {:main "steamdating.core-test"
                        :devcards true
                        :optimizations :none
                        :preloads [devtools.preload]
                        :pretty-print true
                        :output-to "resources/public/js/test.js"
                        :output-dir "resources/public/js/test"
                        :asset-path "js/test"
                        :npm-deps {:feather-icons "3.2.2"}
                        :install-deps true}}}}}
   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
   :production
   {:cljsbuild
    {:builds
     {:client {:compiler {:closure-defines {"goog.DEBUG" false}
                          :optimizations :advanced}}}}}
   :repl [:dev]})
