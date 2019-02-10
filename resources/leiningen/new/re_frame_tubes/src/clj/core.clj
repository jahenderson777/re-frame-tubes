(ns {{ns-name}}.core
    (:require [compojure.core :refer [GET defroutes]]
              [compojure.route :refer [resources]]
              [ring.util.response :refer [resource-response]]
              [org.httpkit.server :refer [run-server]]
              [pneumatic-tubes.core :refer [receiver transmitter dispatch]]
              [pneumatic-tubes.httpkit :refer [websocket-handler]]
              [{{name}}.util :refer :all])
  (:gen-class))

(def tx (transmitter))
(defmulti handle-event (fn [_ [_ event]] event))

(defmethod handle-event :foo
  [tube [_ _ name]]
  (println "Hello " name)
  (dispatch tx tube [:assoc-in [:name] "foo"]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/ws" [] (websocket-handler (receiver {:server #'handle-event})))
  (resources "/"))

(defn -main [& args]
  (def stop-server (run-server (bound-fn [req] (routes req)) {:port 9090})))
