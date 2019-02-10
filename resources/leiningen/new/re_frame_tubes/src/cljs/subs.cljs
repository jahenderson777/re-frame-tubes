(ns {{ns-name}}.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-in
 (fn [db [_ path]]
   (get-in db path)))
