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

(ns learning.jvm)

; Clojure is ported to the JVM, Javascript and CLR. Integrationg with
; the host is quite straightforward. Here I'll show Java integration.

; Strings are Java's strings, as they are immutable and fit nicely with a functional environment
(class "What is this?")

; We may call Java methods easily
(.substring "This is Sparta!" 0 11)

; As well as static methods
(Math/log10
 (/ 1.0
    (- (Math/exp 4) 1.0)))

; Unfortunetely, they are not function types and can't be passed to higher-order functions
(map .toUpperCase ["this" "is" "Sparta"])
(map Math/log10 [1 10 100 1000])

; We can solve this wrapping them inside functions
(map (fn [s] (.toUpperCase s)) ["this" "is" "Sparta"])
(map (fn [i] (Math/log10 i)) [1 10 100 1000])

; We may bring in anything present in the classpath
(import java.net.URL)

; Constructors may be called with new or appended with a period
(new URL "http" "www.google.com" 80 "/index.html")
(URL. "http" "www.google.com" 80 "/index.html")

; Clojure also provides a good interoperability in the reverse way. For some nice examples, check
; http://www.lispcast.com/3-things-java-can-steal-from-clojure‎
