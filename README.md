# a-cat-named-nyan
a very simple java web app integrated with appdirect's APIs

## Requirements
* Java 8

## Running locally
* `./gradlew bootRun`
* or `./gradlew build && java -jar build/libs/a-cat-named-nyan-0.1.war`
* hit `http://localhost:8080`

## Running in a servlet container (Tomcat 8)
* `./gradlew build && java -jar tools/webapp-runner-8.0.30.2.jar build/libs/*.war`
* hit `http://localhost:8080`
