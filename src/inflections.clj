(ns inflections
  (:require [clojure.contrib.str-utils2 :as str2]))

(def *uncountable-words* (atom {}))

(defn- normalize-word [word]
  (str2/lower-case (str2/trim (if (symbol? word) (name word) word))))

(defn- inflect-uncountable [word]
  (let [normalized-word (normalize-word word)]
    (if-not (@*uncountable-words* normalized-word)
      (swap! *uncountable-words* assoc normalized-word word))))

(defmacro uncountable [& words]
  (doseq [word words] (inflect-uncountable word)))

(defn dasherize [word]
  (str2/replace word #"_" "-"))

(defn uncountable? [word]
  (not (nil? (@*uncountable-words* (normalize-word word)))))

(defn underscore [word]
  (str2/replace word #"[-\s]+" "_"))

(uncountable
    air alcohol art blood butter cheese chewing coffee
    confusion cotton education electricity entertainment equipment
    experience fiction fish food forgiveness fresh gold gossip grass
    ground gum happiness history homework honey ice information jam
    knowledge lightning liquid literature love luck luggage meat milk
    mist money music news oil oxygen paper patience peanut pepper
    petrol pork power pressure research rice sadness series sheep
    shopping silver snow space species speed steam sugar sunshine tea
    tennis thunder time toothpaste traffic up vinegar washing wine
    wood wool)
