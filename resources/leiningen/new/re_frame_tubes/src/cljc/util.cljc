(ns {{ns-name}}.util)

(defmacro spy [x]
  (let [line (:line (meta &form))
        file #?(:clj *file*
                :cljs "")]
    `(let [x# ~x]
       (println (pr-str '~x) "is" (pr-str x#)
                (str "; (" ~file ":" ~line ")"))
       x#)))

(defn remove-nth [v n]
  (let [v (into [] v)]
    [(nth v n)
     (concat (subvec v 0 n)
             (subvec v (inc n)))]))

(defn insert-at [row-vec pos item]
  (apply conj (subvec row-vec 0 pos) item (subvec row-vec pos)))

(defn last-idx [coll]
  (dec (count coll)))

(defn find-nth [pred coll]
  (loop [i 0]
    (if (= i (count coll))
      nil
      (if (pred (nth coll i))
        i
        (recur (inc i))))))

(defn hsl [h s l]
  (str "hsl(" h "," s "%," l "%)"))
