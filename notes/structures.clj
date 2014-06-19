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

; Clojure's default data structures are immutable
(def v [\a \b \c \d \e \f \g \h \i \j \k])
v
(def v' (assoc v 5 "middle"))
v'
v

; These data structures are memory-efficient, sharing most of their elements
(def big-vector (vec (range 1e6)))
(def modified-big (assoc big-vector 0 "here's a zero"))
big-vector
modified-big

; Maps may have any immutable value as key, such as vectors
{[0 0] " " [0 1] " " [0 2] "x"
 [1 0] " " [1 1] "x" [1 2] " "
 [2 0] "o" [2 1] "o" [2 2] " "}

; Keywords are interned strings, very efficient for comparisons, which make
; them adequate for map keys
(def person {:name "Kim" :age 25 :loc "here"})

; Also, they are functions that lookup themselves inside a map or set
(:name person)

; Accessing an element with get
(get v 4)
(get person :name)

; get allows a default "not found" value
(get v 100 :not-found)
(get person :occupation :unemployed)

; vectors and maps are functions that lookup the element inside them
(v 4)
(person :loc)
