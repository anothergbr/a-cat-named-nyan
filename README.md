# a-cat-named-nyan
[![Build Status](https://travis-ci.org/anothergbr/a-cat-named-nyan.svg?branch=master)](https://travis-ci.org/anothergbr/a-cat-named-nyan)

a very simple java web app integrated with appdirect's APIs

## Requirements
* Java 8

## Running the tests
* `./gradlew test`

## Running locally
* `./gradlew bootRun`
* or `./gradlew stage && java -jar build/libs/a-cat-named-nyan-0.1.war`
* hit `http://localhost:8080`

## Running in a servlet container (Tomcat 8)
* `./gradlew stage && java -jar build/libs/webapp-runner-8.0.30.2.jar build/libs/*.war`
* hit `http://localhost:8080`

## Getting the war
* `./gradlew stage`
* the war is in `build/libs/a-cat-named-nyan-*.war`

## Running in IntelliJ 16
1. `Run` -> `Edit Configurations` -> `Add new configuration` -> Pick `Application` type.
2. Set `Main class` to `com.gbr.Application`
3. Set `Use classpath of module` to `a-cat-named-nyan_test` (the test module!)
    * required because of [Intellij bug with provided configurations](https://youtrack.jetbrains.com/issue/IDEA-107048)
4. `Ok` and `Run` it!
