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

(class "What is this?")

; "This is Sparta!".substring(0, 11);

(Math/log10
  (/ 1.0
     (- (Integer/valueOf "42") 1.0)))

(map .toUpperCase ["this" "is" "Sparta"])
(map Math/log10 [1 10 100 1000])

(import java.net.URL)
(new URL "http" "www.google.com" 80 "/index.html")
