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

; We can define variables in our namespace using def
(def v [1 2 3 4 5 6 7 8 9 10])

; Functions are defined with defn in the following format:
(defn incrementer
  [x]
  (+ x 1))

; Functions may take other functions as arguments
(defn call-if-positive
  [f x]
  (if (pos? x)
    (f x)
    nil))
(call-if-positive incrementer -1)
(call-if-positive incrementer 0)
(call-if-positive incrementer 1)
(call-if-positive println 10)

; For some cases, it may be better to create anonymous, on-the-spot functions with fn
(call-if-positive (fn [x] (* x 2)) 10)

; defn is, basically, def + fn (although defn has other features, such as docstrings)
(def decrementer (fn [x]
                   (- x 1)))
(call-if-positive decrementer 10)

; You can create functions that return functions (more correctely, closures)
(defn make-range-checker
  "This function returns a function that tests if a
  parameter x is strictly between lower and upper, ie, lower < x < upper"
  [lower upper]
  (fn [x]
    (< lower x upper)))
(make-range-checker 3 9)
((make-range-checker 3 9) 1)
((make-range-checker 3 9) 4)

; Let's put them to use
(defn guessing-game
  [x]
  "This is a silly guessing game. Note how we use the returned functions from make-range-checker
  with one parameter, with lower and upper closed inside the function.
  Use let, not def, to declare internal variables."
  (let [in-loose? (make-range-checker 10 50),
        in-tight? (make-range-checker 20 30)]
    (if (in-tight? x)
      "You guessed great!"
      (if (in-loose? x)
        "You guessed ok..."
        "You guessed badly!"))))
(guessing-game 2)
(guessing-game 80)
(guessing-game 40)
(guessing-game 29)
