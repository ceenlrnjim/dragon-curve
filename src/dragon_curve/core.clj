(ns dragon-curve.core)

(def opposite {:left :right :right :left :up :down :down :up})

(defn compute-next-path [prev]
  (let [n (* 0.5 (count prev))] ; or .25 of this length which is 2*prev
    (vec (concat (take n prev) ; 1/4 copied as is
              (map opposite (take n (drop n prev))) ; 1/4 is inverted
              prev)))); last half is duplicated from previous step

(defn paths 
  ([t] (paths t 2 []))
  ([t i result]
    (cond (< t i) result
          (= i 2) (recur t 3 [[:left] [:down] [:right :down]])
          :else (let [next-path (compute-next-path (last result)) ]
                  (recur t (inc i) (conj result next-path))))))


