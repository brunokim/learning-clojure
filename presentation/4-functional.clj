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

(seq [\g \o \o \g \l \e])

(seq #{2 3 5 7 11 13 17 19 23 29})

(seq {:name "Kim" :age 25 :loc "here"})

(def from-1-to-10 (range 1 11))
(def naturals (range))

(filter (fn [entry]
          (let [key (first entry), value (second entry)]
            (string? value)))
        {:name "Kim", :age 25, :loc "Here"})

(map (fn [s]
       (count s))
     ["apple pie" "brigadeiro" "cupcake" "doughnut" "eclair" "froyo" "gingerbread"])

(mapv int [\G \o \o \g \l \e])

(reduce (fn [acc ch]
          (+ acc (int ch)))
        0
        "Happy new year to you!")

(defn collatz-step
  [x]
  (if (even? x)
    (/ x 2)
    (+ 1 (* 3 x))))

(defn collatz
  [n]
  (iterate collatz-step n))
