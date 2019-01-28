(ns {{ns-name}}.events
  (:require
   [re-frame.core :refer [reg-event-db dispatch after]]
   [pneumatic-tubes.core :as tubes]{{#10x?}}
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]{{/10x?}}
   ))

(def default-db
  {:name "re-frame"})

(reg-event-db
 ::initialize-db
 ({{^10x?}}fn{{/10x?}}{{#10x?}}fn-traced{{/10x?}} [_ _]
  default-db))

(defn on-receive [event-v]
  (.log js/console "received from server:" (str event-v))
  (dispatch event-v))

(def tube (tubes/tube (str "ws://localhost:9090/ws") on-receive))
(def send-to-server (after (fn [_ v] (tubes/dispatch tube v))))

(reg-event-db
 :assoc-in
 (fn [db [_ path val]]
   (assoc-in db path val)))

(reg-event-db
 :server
 send-to-server
 (fn [db [_ _ _ name]]
   (.log js/console (str "Hello " name))
   db))

(tubes/create! tube)
