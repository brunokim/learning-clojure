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

(ns learning.syntax) ; ns = namespace. Pay no attention to it.

; Clojure is a Lisp, like Racket, Scheme and Common Lisp. Lisp programs are made of lists.
; The first element in the list is the function to be called. The rest are arguments to this function.
(+ 2 2)
(println "Hello World!")
(and true false)

; Each list is an expression, so we may nest lists arbitrarily to produce more complex expressions
(/ (+ 7 1)
   (- (* 2 3) 2))

; Prefix notation makes arithmetic and logic expressions weirder than infix notation.
; However, it bring some benefits, like easily providing variable arguments
(+ 1 2 3 4 5 6 7 8 9 10)
(- 4 1 1 5 3)

; Just some more bits of syntax
(print \c \newline \u2603) ; characters are preceded by a backslash
"strings are enclosed in double quotes"
:keyword ; keywords are a special type of string, and are preceded with a colon
; Numbers have builtin suport for bigdecimal, bigintegers and rationals
10 10.0 10.02M 1000000000000000N 10/3

; Clojure also has some builtin data structures that are represented with special syntax

[\g \o \o \g \l \e]                    ; vectors
{:name "Kim" :age 25 :location "here"} ; maps
#{2 3 5 7 11 13 17 19 23 29}           ; sets
'(1 2 3 4 5) ; proper lists, which needs a preceding quote to prevent execution
(1 2 3 4 5) ; without the quote we see an exception: "java.lang.Long cannot be cast to clojure.lang.IFn"
; This means that a number type (java.lang.Long) cannot be called as a function type (clojure.lang.IFn)

; Commas are whitespace, and may be used for better readability
{:name "Mike Tyson", :skills ["punching", "standing", "acting", "pigeon racing"]}

; Almost everything is an expression. For example, if has the form
; (if condition
;   then-part
;   else-part)
; It will return, not execute, either the then part or the else part
(if (neg? 10)
  "10 is negative"
  "10 is not negative")

; There are other syntax goodies, like literal lambdas and deconstruction, that won't be shown here.
