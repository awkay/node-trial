(ns node-trial.client-test-main
  (:require node-trial.tests-to-run
            [fulcro-spec.selectors :as sel]
            [fulcro-spec.suite :as suite]))

(enable-console-print!)

(suite/def-test-suite client-tests {:ns-regex #"node-trial..*-spec"}
  {:default   #{::sel/none :focused}
   :available #{:focused}})

