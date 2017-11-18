(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.8.2"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "4.6.1")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/pixi
       :version     +version+
       :description "2D webGL renderer with canvas fallback"
       :url         "http://www.pixijs.com"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://cdnjs.cloudflare.com/ajax/libs/pixi.js/" +lib-version+ "/pixi.min.js")
              :checksum "E5FAEA102D9B83A33A34BBB195F78C9C")
    (download :url (str "https://cdnjs.cloudflare.com/ajax/libs/pixi.js/" +lib-version+ "/pixi.js")
              :checksum "F6B2DCBFA0E62DDABB75560767E582F5")
    (sift :move {#"pixi\.js$" "cljsjs/pixi/development/pixi.inc.js"
                 #"pixi\.min\.js$" "cljsjs/pixi/development/pixi.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.pixi")
    (pom)
    (jar)))
