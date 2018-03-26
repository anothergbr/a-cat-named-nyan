# a-cat-named-nyan
[![Build Status](https://travis-ci.org/gbranchaudrubenovitch/a-cat-named-nyan.svg?branch=master)](https://travis-ci.org/gbranchaudrubenovitch/a-cat-named-nyan) [![Coverage Status](https://coveralls.io/repos/github/gbranchaudrubenovitch/a-cat-named-nyan/badge.svg?branch=master)](https://coveralls.io/github/gbranchaudrubenovitch/a-cat-named-nyan?branch=master)

![an image of nyan cat](http://i.imgur.com/880eyVm.gif "Nyan Cat")

This was made as part of the AppDirect 2015 coding challenge.
It's a very simple java web app integrated with some of Appdirect's subscription APIs.

## Requirements
* Java 8+

## Running the tests
* `./gradlew test`

## Running locally
* `./gradlew bootRun`
* or `./gradlew stage && java -jar build/libs/a-cat-named-nyan-0.1.war`
* hit `http://localhost:8080`

## Running in a servlet container (Tomcat 8)
* `./gradlew stage && java -jar build/libs/webapp-runner-8.5.29.0.jar build/libs/*.war`
* hit `http://localhost:8080`

## Getting the war
* `./gradlew stage`
* the war is in `build/libs/a-cat-named-nyan-*.war`
