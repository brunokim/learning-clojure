; Copyright 2014 Google Inc. All rights reserved.
; 
; Licensed under the Apache License, Version 2.0 (the License);
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
;     http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an AS IS BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.

(ns learning.concurrency
  (:require [clojure.core.async :refer [chan >! <! go]]))

; Futures

(slurp "http://www.google.com")

; core.async

(def network (chan 10))

(go
 (>! network "IMPORTANT")
 (>! network "TARGET: JOHN CONNOR")
 (>! network "YEAR: 1995"))

(go (while true (println (<! network))))

(go (>! network "AGENT: TERMINATOR"))

; atoms

(def counter (atom 0))
@counter

(reset! counter 10)

; refs

(def prey (ref {:name "Kim" :blood 15}))
(def ancilla (ref {:name "Ecaterina" :blood 12 :max-blood 20}))
(def neonate (ref {:name "Christof" :blood 3 :max-blood 15}))
(defn total-blood []
  (reduce + (map :blood [@prey @ancilla @neonate])))

(defn suck-blood
  "A vampire sucks blood from a defenseless victim."
  [vampire victim]
  (dosync
   (when (and (< (:blood @vampire) (:max-blood @vampire))
              (pos? (:blood @victim)))
     (alter victim (fn [state]
                     (assoc state :blood (dec (:blood state)))))
     (alter vampire (fn [state]
                      (assoc state :blood (inc (:blood state))))))))

(future (dotimes [i 20] (Thread/sleep 1) (suck-blood neonate prey)))
(future (dotimes [i 20] (Thread/sleep 1) (suck-blood ancilla prey)))
