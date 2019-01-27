(defproject {{ns-name}} "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.6"]
                 [compojure "1.5.0"]
                 [http-kit "2.3.0"]
                 [pneumatic-tubes "0.3.0"]
                 [org.clojure/core.async "0.4.490"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]{{#10x?}}
                   [day8.re-frame/re-frame-10x "0.3.3"]
                   [day8.re-frame/tracing "0.5.1"]{{/10x?}}
                   [figwheel-sidecar "0.5.18"]
                   [cider/piggieback "0.3.10"]{{#re-frisk?}}
                   [re-frisk "0.5.3"]{{/re-frisk?}}]

    :plugins      [[lein-figwheel "0.5.18"]]}

   :prod { {{#10x?}}:dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]{{/10x?}}}
   :uberjar {:source-paths ["env/prod/clj"]{{#10x?}}
             :dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]{{/10x?}}
             :omit-source  true
             :main         {{ns-name}}.server
             :aot          [{{ns-name}}.server]
             :uberjar-name "{{name}}.jar"
             :prep-tasks   ["compile" ["cljsbuild" "once" "min"]]}
   }

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "{{name}}.core/mount-root"}
     :compiler     {:main                 {{name}}.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload{{#10x?}}
                                           day8.re-frame-10x.preload{{/10x?}}{{#re-frisk?}}
                                           re-frisk.preload{{/re-frisk?}}]{{#10x?}}
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true
                                           "day8.re_frame.tracing.trace_enabled_QMARK_" true}{{/10x?}}
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            {{name}}.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}
    ]}
  )
