(ns leiningen.new.re-frame-tubes
  (:require [leiningen.new.templates :refer [renderer name-to-path sanitize-ns ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "re-frame-tubes"))



(defn re-frame-tubes
  "FIXME: write documentation"
  [name & options]
  (let [data {:name name
              :ns-name   (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' re-frame-tubes project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render ".gitignore" data)]
             ["resources/public/index.html" (render "resources/public/index.html" data)]
             ["src/clj/{{sanitized}}/core.clj" (render "src/clj/core.clj" data)]
             ["src/cljs/{{sanitized}}/core.cljs" (render "src/cljs/core.cljs" data)]
             ["src/cljs/{{sanitized}}/subs.cljs" (render "src/cljs/subs.cljs" data)]
             ["src/cljs/{{sanitized}}/views.cljs" (render "src/cljs/views.cljs" data)]
             ["src/cljs/{{sanitized}}/events.cljs" (render "src/cljs/events.cljs" data)]
             )))
