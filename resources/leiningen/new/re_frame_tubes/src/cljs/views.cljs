(ns {{ns-name}}.views
  (:require
   [re-frame.core :as re-frame]
   [{{ns-name}}.events :as events]
   [{{ns-name}}.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:a {:on-click #(re-frame/dispatch [:server :foo])} "click me" ]
     ]))
