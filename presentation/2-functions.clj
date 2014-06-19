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

(ns learning.functions)

(def v [1 2 3 4 5 6 7 8 9 10])

(def decrementer (fn [x]
                   (- x 1)))

(defn incrementer
  [x]
  (+ x 1))

(defn call-if-positive
  [f x]
  (if (pos? x)
    (f x)
    nil))

(defn make-range-checker
  "This function returns a function that tests if a
  parameter x is strictly between lower and upper, ie, lower < x < upper"
  [lower upper]
  (fn [x]
    (< lower x upper)))
