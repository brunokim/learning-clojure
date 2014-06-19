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

(ns learning.macros)

(list + 1 2)

(defn infix-to-prefix [form]
  (assert (= 3 (count form)) "Sorry, no expression parsing")
  (let [arg1     (nth form 0)
        function (nth form 1)
        arg2     (nth form 2)]
    (list function arg1 arg2)))

(defmacro captain-obvious
  [form]
  (list str
        "The form "
        (list 'quote form)
        " evaluates to <"
        form
        ">"))

(let [x true]
  (if x
    (let [x false]
      (if x
        (let [x (println "Not gonna print")]
          (if x
            (throw (Exception. "Neither gonna throw"))
            x))
        x))
    x))
