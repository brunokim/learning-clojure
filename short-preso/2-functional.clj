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

(def decrementer (fn [x]
                   (- x 1)))

(seq {:a 1, :e 2, :i 3 :o 4, :u 5})
(seq [\a \e \i \o \u])
(seq "aeiou")

(filter (fn [entry]
          (let [key (first entry), value (second entry)]
            (string? value)))
        {:name "Kim", :age 25, :location "Here"})

(map (fn [s]
       (count s))
     ["apple pie" "brigadeiro" "cupcake" "doughnut" "eclair" "froyo" "gingerbread"])

(reduce (fn [acc ch]
          (+ acc (int ch)))
        0
        "Happy new year to you!")

(defn make-range-checker
  [lower upper]
  (fn [x]
    (< lower x upper)))
