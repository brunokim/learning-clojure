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

; As Lisps are written themselves as data structures, generating code with them is a
; lot easier than in other languages. Macros are a way to generate code in compile time.

; Clojure evaluates data structures, which we call forms. This is evident when you create your own.
(list + 1 2)
(eval (list + 1 2))

; If we manipulate these data structures, we can create new syntax for the language.
; For example, we could try writing arithmetic operations in infix notation when necessary.
; First, let's write a form-manipulating function.
(defn infix-to-prefix [form]
  (assert (= 3 (count form)) "Sorry, no expression parsing")
  (let [arg1     (nth form 0)
        function (nth form 1)
        arg2     (nth form 2)]
    (list function arg1 arg2)))
(infix-to-prefix '(1 + 2))
(infix-to-prefix '(1 + 2 + 3 * 4))
(infix-to-prefix '((1 + 2) / (3 * 4))) ; Oops

; If one of the args is itself a form, we should recursively change it too.
(defn infix-to-prefix [form]
  (assert (= 3 (count form)) "Sorry, no expression parsing")
  (let [swap-if-form (fn [arg] (if (seq? arg)
                                 (infix-to-prefix arg)
                                 arg))
        arg1     (swap-if-form (nth form 0))
        function (nth form 1)
        arg2     (swap-if-form (nth form 2))]
    (list function arg1 arg2)))
(infix-to-prefix '(1 + 2))
(infix-to-prefix '((1 + 2) / (3 * 4)))
(infix-to-prefix '(1 + 2 + 3 * 4))

; Now, we could use eval to work with these functions, but it's ugly and it works
; in runtime. It wouldn't catch compilation errors, for example.
(defn arithmetic [form]
  (eval (infix-to-prefix form)))
(arithmetic '(1 + 2))
(arithmetic '((1 + 2) / (3 * 4)))
(arithmetic '(1 + 2 + 3 * 4))

; Here come macros! They take a form and convert it to another form during compilation.
; Evaluation will happen in runtime, as with any other form.
(defmacro infix [form]
  (infix-to-prefix form))
(infix (1 + 2))
(infix ((1 + 2) / (3 * 4)))
(infix (1 + 2 + 3 * 4))

; Let's sidetrack a little to know the quote form. It returns the given form without evaluation.
(quote (+ 1 2))
; The ' character, which we use to declare lists, is simply an alias for quote
'(+ 1 2)
; To create a quote form, we need to quote the quote :)
(list 'quote '(+ 1 2))
; Together with macros, we can easily create metaprogramming functionality
(defmacro captain-obvious
  [form]
  (list str
        "The form "
        (list 'quote form)
        " evaluates to <"
        form
        ">"))
(captain-obvious (+ 1 2))

; Creating macros with list operations gets old quickly. Thankfully, there's special syntax that
; allows manipulating forms in a way similar to templating.
; Think of the backtick ` (syntax-quote) as "template-on" and ~ (unquote) as "template-off".
(defmacro captain-obvious
  [form]
  `(str "The form "
        (quote ~form)
        " evaluates to <"
        ~form
        ">"))
(captain-obvious (str "is obvious"))

; Macros can also be used to circumvent eager evaluation. The if form is special in that it only
; evaluate one of the code paths. "and" is a macro built upon it to create short-circuit evaluation.
(and true
     false
     (println "Not gonna print")
     (throw (Exception. "Neither gonna throw")))
; This form is expanded to the following during compilation:
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
