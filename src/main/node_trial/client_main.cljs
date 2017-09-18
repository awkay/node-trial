(ns node-trial.client-main
  (:require [node-trial.client :as client]
            [fulcro.client.core :as core]
            [node-trial.ui.root :as root]))

; This is the production entry point. In dev mode, we do not require this file at all, and instead mount (and
; hot code reload refresh) from cljs/user.cljs
(reset! client/app (core/mount @client/app root/Root "app"))
