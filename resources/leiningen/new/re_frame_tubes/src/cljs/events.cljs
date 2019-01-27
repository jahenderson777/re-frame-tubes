(ns {{ns-name}}.events
  (:require
   [re-frame.core :as re-frame]
   [pneumatic-tubes.core :as tubes]{{#10x?}}
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]{{/10x?}}
   ))

(def default-db
  {:name "re-frame"})

(re-frame/reg-event-db
 ::initialize-db
 ({{^10x?}}fn{{/10x?}}{{#10x?}}fn-traced{{/10x?}} [_ _]
  default-db))

(defn on-receive [event-v]
  (.log js/console "received from server:" (str event-v))
  (re-frame/dispatch event-v))

(def tube (tubes/tube (str "ws://localhost:9090/ws") on-receive))
(def send-to-server (re-frame/after (fn [_ v] (tubes/dispatch tube v))))

(re-frame/reg-event-db
 :server
 send-to-server
 (fn [db [_ _ name]]
   (.log js/console (str "Hello " name))
   db))

(re-frame/reg-event-db
 :say-hello-processed
 (fn [db _]
   (.log js/console "Yay!!!")
   db))

(tubes/create! tube)
