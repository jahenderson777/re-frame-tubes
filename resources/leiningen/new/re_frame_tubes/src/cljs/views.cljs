(ns {{ns-name}}.views
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [{{ns-name}}.events :as events]
   [{{ns-name}}.subs :as subs]))

(defn <- [v]
  (deref (subscribe v)))

(defn main-panel []
  [:div
   [:h1 "Hello from " (<- [:get-in [:name]])]
   [:a {:on-click #(dispatch [:server :foo [:assoc-in [:name]] "baz"])}
    "click me"]
   [:pre (str (<- [:get-in]))]])
