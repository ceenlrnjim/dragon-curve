(ns dragon-curve.render
  (:import (java.awt Graphics Color Font RenderingHints)
           (javax.swing JButton JFrame JComponent JScrollPane))
  (:use dragon-curve.core))

(def LINESIZE 10)

(defn endpoint [[x y] dir]
  (cond (= dir :up) [x (- y LINESIZE)]
        (= dir :down) [x (+ y LINESIZE)]
        (= dir :left) [(- x LINESIZE) y]
        (= dir :right) [(+ x LINESIZE) y]))

(defn draw-line [g start dir]
  (let [[x y] start
        [x2 y2] (endpoint start dir)]
    ;(println "Drawing: " x y x2 y2)
    (.drawLine g x y x2 y2)
    [x2 y2]))

; renders a single path
(defn draw-path [g pos p]
  (loop [start pos
         path p]
    (if (seq path) 
      (recur (draw-line g start (first path)) (rest path))
      start)))

(defn curve-visualization [n]
  (let [routes (paths n)
        component (proxy [JComponent] []
               (paint [g]
                 (.setColor g Color/BLUE)
                  (draw-path g [300 600] (last (paths n)))
                 ))
        ]
    component))


(let [frame (JFrame.)]
  (.add (.getContentPane frame) (JScrollPane. (curve-visualization (Integer/parseInt (second *command-line-args*)))))
  (doto frame
    (.setBounds 100 0 1000 800)
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
    (.setVisible true)
    )) 
 

