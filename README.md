# a-cat-named-nyan
a very simple java web app integrated with appdirect's APIs

## Requirements
* Java 8

## Running locally
* `./gradlew bootRun`
* hit `http://localhost:8080`

## Building a `war`
* `./gradlew build`
* the `war` is in `./build/libs/*.war`
    * you can run it with an embedded tomcat: `java -jar tools/webapp-runner-8.0.30.2.jar build/libs/*.war`
    * hit `http://localhost:8080`
