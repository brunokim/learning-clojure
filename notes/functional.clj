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

(ns learning.functional)

; Clojure is a functional language, but not a pure one.
; Clojure has a single abstraction to operate in data structures, called a seq.

; The seq of a vector is a list of its elements in order
(seq [\g \o \o \g \l \e])

; The seq of a set is a list of its elements, probably out of order
(seq #{2 3 5 7 11 13 17 19 23 29})

; The seq of a map is a list of entries, given as two-element vectors
(seq {:name "Kim" :age 25 :loc "here"})

; Most seqs in Clojure are lazily-evaluated, which allows for infinite collections
(def from-1-to-10 (range 1 11))
(def naturals (range))
from-1-to-10
naturals

; The triad of higher-order functions - filter, map, reduce - operate over seqs
(filter (fn [entry]
          (let [key (first entry), value (second entry)]
            (string? value)))
        {:name "Kim", :age 25, :loc "Here"})

(map (fn [s]
       (.toUpperCase s))
     ["apple" "banana" "carrot"])

; If you want to map over a vector and preserve its type, use mapv
(mapv int [\G \o \o \g \l \e])

; Strings are seqs of characters
(reduce (fn [acc c]
          (+ acc (int c)))
        0
        "Happy new year to you!")

; reduce may be provided without an initial value, if the accumulator has the
; same type as the elements.
(reduce + [1 2 3 4 5 6 7 8 9 10])
(reduce + 10 [1 2 3 4 5 6 7 8 9 10])

; iterate returns an infinite lazy seq of x, (f x), (f (f x)), ...
; Good for recursive algorithms
(defn collatz
  [x]
  (iterate (fn [n]
             (if (even? n)
               (/ n 2)
               (+ 1 (* 3 n))))
            x))
(collatz 11)

; take limits the number of items taken from a seq
(take 120 (collatz 27))

; take-while expects a predicate to limit the number of items taken
(take-while (fn [n] (not= n 1)) (collatz 27))
