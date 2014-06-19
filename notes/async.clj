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

(ns learning.async)

; Clojure initial goal was to provide a language with sensible choices for writing
; concurrent/parallel/asynchronous applications.

; futures hold computations that may take a long time
(def future-html (future (let [page (slurp "http://www.google.com")]
                           (println page) ; prints and return the page
                           page)))
(realized? future-html)
; Will block until the future is ready. Further derefs won't take time
@future-html
@future-html

; Another similar concept is a promise, that waits on a deliver instead of on a computation.
(def place (promise))
(def hour (promise))

; invitation waits on both promises
(def invitation (future (str "Come to " @place " at " @hour " for a party!")))
(realized? invitation)

(deliver place "my room")
(realized? invitation)

(deliver hour "20h")
(realized? invitation)
@invitation

; core.async is an ongoing development that provides asynchronous communication channels
(require '[clojure.core.async :refer [chan >! <! go]])

; Create a channel with a given buffer size.
(def network (chan 10))

; Put messages with >! (that's an arrow '>' with a bang to indicate mutability)
(go
 (>! network "IMPORTANT")
 (>! network "TARGET: JOHN CONNOR")
 (>! network "YEAR: 1995"))

; Take messages with <!. The infinite loop will park waiting for more inputs.
(go (while true (println (<! network))))

; Provide some more input...
(go (>! network "AGENT: TERMINATOR"))

; go blocks provide lightweight threads, which are also available in Clojurescript. With this it's possible
; to ditch callback/handler event-oriented paradigm to work with communication channels.

; In the JVM, you can use regular threads with "double bangs" operations like <!! and >!!. core.async provides
; many more interesting operations, like alternatives selector, multiplexing and more.
