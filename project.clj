(defproject node-trial "0.1.0-SNAPSHOT"
  :description "My Cool Project"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}
  :min-lein-version "2.7.0"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.931"]
                 ;; IMPORTANT STEP 1: Make sure react is not coming from cljsjs ********************************************************************************
                 [org.omcljs/om "1.0.0-beta1" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [fulcrologic/fulcro "1.0.0-beta10"]
                 [fulcrologic/fulcro-spec "1.0.0-beta9" :scope "test" :exclusions [org.omcljs/om fulcrologic/fulcro]]]

  :uberjar-name "node_trial.jar"

  :source-paths ["src/main"]
  :test-paths ["src/test"]
  :clean-targets ^{:protect false} ["target" "resources/public/js" "resources/private"]

  :profiles {:dev {:source-paths ["src/dev" "src/main" "src/test" "src/cards"]

                   :jvm-opts     ["-XX:-OmitStackTraceInFastThrow" "-client" "-XX:+TieredCompilation" "-XX:TieredStopAtLevel=1"
                                  "-Xmx3g" "-Xms2g" "-XX:+UseConcMarkSweepGC" "-XX:+CMSClassUnloadingEnabled" "-Xverify:none"]

                   :figwheel     {:css-dirs        ["resources/public/css"]
                                  :validate-config false}

                   :cljsbuild    {:builds
                                  [{:id           "cards"
                                    :figwheel     {:devcards true}
                                    :source-paths ["src/main" "src/cards"]
                                    :compiler     {:asset-path           "js/cards"
                                                   :main                 node-trial.cards
                                                   :optimizations        :none
                                                   :output-dir           "resources/public/js/cards"
                                                   :output-to            "resources/public/js/cards.js"
                                                   :preloads             [devtools.preload]
                                                   ;; IMPORTANT STEP 2: Make the NPM react also act AS IF it were the cljsjs version ********************************************************************************
                                                   :foreign-libs         [{:provides       ["cljsjs.react"]
                                                                           :file           "node_modules/react/dist/react.js"
                                                                           :global-exports {cljsjs.react React}}
                                                                          {:provides       ["cljsjs.react.dom"]
                                                                           :file           "node_modules/react-dom/dist/react-dom.js"
                                                                           :global-exports {cljsjs.react.dom ReactDOM}}]
                                                   ;; IMPORTANT STEP 3: Add in the correct NPM dependencies********************************************************************************
                                                   :install-deps         true
                                                   :npm-deps             {:react                             "15.5.4"
                                                                          :react-dom                         "15.5.4"
                                                                          :react-addons-css-transition-group "15.6.0"
                                                                          "@blueprintjs/core"                "1.28.0"
                                                                          }
                                                   :parallel-build       true
                                                   :source-map-timestamp true}}
                                   ]}

                   :plugins      [[lein-cljsbuild "1.1.7"]
                                  [lein-doo "0.1.7"]
                                  [com.jakemccrary/lein-test-refresh "0.17.0"]]

                   :dependencies [[binaryage/devtools "0.9.4"]
                                  [org.clojure/tools.namespace "0.3.0-alpha4"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [lein-doo "0.1.7" :scope "test"]
                                  [figwheel-sidecar "0.5.13" :exclusions [org.clojure/tools.reader]]
                                  [devcards "0.2.3" :exclusions [cljsjs/react cljsjs/react-dom]]]
                   :repl-options {:init-ns          user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
