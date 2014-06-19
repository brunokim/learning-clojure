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

(ns learning.concurrency)

; Clojure provides great native support for different kinds of controlled mutability using
; software transactional memory (STM).
; STM is said to be to concurrency what garbage collection is to memory management.

;;;;;;;;;;;;;;;
;;;; Atoms ;;;;
;;;;;;;;;;;;;;;

; An atom gives a reference to a shared state which is mutated atomically
(def counter (atom 0))
; Dereference with @
@counter

; To change the value of an atom, use reset!
(reset! counter 10)

; To compute a value for an atom using its current value, use swap!
(swap! counter inc)
(swap! counter inc)
(swap! counter inc)

;;;;;;;;;;;;;;;;
;;;; Agents ;;;;
;;;;;;;;;;;;;;;;

; Agents are similar to atoms, except that its mutating action runs in another thread.
; They are not autonomous as actors, but are used for related tasks.
(def drunkard (agent 0))

; Use send when the computation is non-blocking. It will be executed in a thread pool
; with fixed size, in general (number of available processors) + 1.
(defn walk
  "Tells an agent to walk one step"
  [a step]
  (send a (fn [x] (+ x step))))
(walk drunkard (rand-nth [-1 +1]))

; rand-nth internally uses a pseudo-random number generator, which is not enough for
; our needs. Let's fetch random bits from random.org, which are truly random.
(require '[clojure.data.json :as json]
         '[clj-http.client :as client])

(defn random-bits
  "Retrieves n truly random bits from random.org"
  [n]
  (let [request {:jsonrpc "2.0" :id 0
                 :method "generateIntegers"
                 :params {:apiKey "c46df75f-301d-4df6-bd86-d4527fdc83d7"
                          :n n :min 0 :max 1}}
        response (client/post "https://api.random.org/json-rpc/1/invoke"
                              {:content-type :json, :body (json/write-str request), :as :json})]
    (get-in response [:body :result :random :data])))
#_(random-bits 100)

; Use send-off when the computation is blocking. It will be executed in an elastic thread
; pool, so that multiple blocking operations may run concurrently.
(defn random-walk
  "Tells an agent to perform n random steps"
  [a n]
  (send-off a
    (fn [x]
      (reduce (fn [position bit]
                (+ position (get [-1 +1] bit)))
              x
              (random-bits n)))))
(random-walk drunkard 100)
@drunkard

;;;;;;;;;;;;;;
;;;; Refs ;;;;
;;;;;;;;;;;;;;

; Atoms and agents are used for uncoordinated access. If you need to coordinate mutations
; to several mutable references, you can store their states inside a ref and perform all
; mutations inside a transaction.
(def prey (ref {:name "Kim" :blood 15}))
(def ancilla (ref {:name "Ecaterina" :blood 12 :max-blood 20}))
(def neonate (ref {:name "Christof" :blood 3 :max-blood 15}))
(defn total-blood []
  (reduce + (map :blood [@prey @ancilla @neonate])))

; This is the initial total amount of blood
(total-blood)

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

; Two vampires try to suck blood from the same victim at the same time.
(future (dotimes [i 20] (Thread/sleep 1) (suck-blood neonate prey)))
(future (dotimes [i 20] (Thread/sleep 1) (suck-blood ancilla prey)))
(println @prey @ancilla @neonate)

; The final total amount of blood didn't change, indicating there were no race conditions
(total-blood)
