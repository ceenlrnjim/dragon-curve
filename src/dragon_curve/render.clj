(ns dragon-curve.render
  (:import (java.awt Graphics Color Font RenderingHints)
           (javax.swing JButton JFrame JComponent JScrollPane))
  (:use dragon-curve.core))

(def linesize (atom  10))
(def iterations (atom 10))
(def start-x (atom 500))
(def start-y (atom 400))

(defn endpoint [[x y] dir]
  (cond (= dir :up) [x (- y @linesize)]
        (= dir :down) [x (+ y @linesize)]
        (= dir :left) [(- x @linesize) y]
        (= dir :right) [(+ x @linesize) y]))

(defn draw-line [g start dir]
  (let [[x y] start
        [x2 y2] (endpoint start dir)]
    ;(println "Drawing: " x y x2 y2 @linesize)
    (.drawLine g x y x2 y2)
    [x2 y2]))

; renders a single path
(defn draw-path [g pos p]
  (loop [start pos
         path p]
    (if (seq path) 
      (recur (draw-line g start (first path)) (rest path))
      start)))

(defn curve-visualization []
  (let [routes (flatten (paths @iterations))
        component (proxy [JComponent] []
               (paint [g]
                 (.setColor g Color/BLUE)
                  (draw-path g [@start-x @start-y] routes))) ]
    component))


(defn -main [optionstring]
  (let [frame (JFrame.)
        options-map (read-string optionstring)]
    (swap! linesize (fn [_] (:linesize options-map)))
    (swap! iterations (fn [_] (:iterations options-map)))
    (swap! start-x (fn [_] (:x options-map)))
    (swap! start-y (fn [_] (:y options-map)))
    (println options-map)
    (println @linesize @iterations @start-x @start-y)
      (.add (.getContentPane frame) (JScrollPane. (curve-visualization)))
      (doto frame
        (.setBounds 100 0 1000 800)
        (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
        (.setVisible true))))
 

