(ns {{ns-name}}.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :refer [dispatch subscribe] :as re-frame]
   [{{ns-name}}.events :as events]
   [{{ns-name}}.subs :as subs]))

(def debug? ^boolean goog.DEBUG)

(defn <- [v]
  (deref (subscribe (vec v))))

(defn main-panel []
  [:div
   [:h1 "Hello from " (<- [:get-in [:name]])]
   [:a {:on-click #(dispatch [:server :foo [:assoc-in [:name]] "baz"])}
    "click me"]
   [:pre (str (<- [:get-in]))]])

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [main-panel] (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (when debug? (enable-console-print!))
  (mount-root))
