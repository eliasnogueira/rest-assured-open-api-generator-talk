# How to fast generate your API Test with OpenAPI Tools and Rest-Assured

This repo is directly linked with the [How to fast generate your API Test with OpenAPI Tools and REST Assured](https://speakerdeck.com/eliasnogueira/how-to-fast-generate-your-api-test-with-openapi-tools-and-rest-assured)

Please, take a look at the presentation.

## What will you find here

### Source

All references are from the `src/main/java` folder.

* `se.jfokus.workshop.models` have a Model/POJO class for the Simulation request and response
* `se.jfokus.workshop.restassured.specification` have the request and response specifications for each API
* `se.jfokus.workshop.api` have the RestApiClientBuilder to build the auto-generated clients from the OpenAPI generator
* `se.jfokus.workshop.api.client` have the Restrictions API Client implementation example using the auto-generated client from the OpenAPI tools
* `se.jfokus.workshop.api.service` have the Restrictions Service implementation based on its client

### Tests

All references are from the `src/test/java` folder.

* `BaseApConfiguration` as the BaseTest class to configure the application endpoints and general config
    * note: the `RestAssured.basePath` is not necessary when running the *architecture* tests
* `se.jfokus.workshop.*.raw` package: you will find the "raw" REST Assured tests using it basic `given - when - then` syntax
* `se.jfokus.workshop.*.spec` package: you will find the REST Assured tests using the Request and Response Specifications
* *`se.jfokus.workshop.*.architecture` package: you will find the REST Assured tests using an architecture based on client and services abstractions together with the generated OpenAPI client

### pom.xml

The focus here is the explanation about the auto-generation classes based on an Open API specification, and you need to look at the `<build>` section.

#### Download the Open API specification

To auto-generate the necessary client and classes using the Open API Generator we need to download the spec.
This is done using the `wagon-maven-plugin` plugin downloading the target Open API spec all the time we generate the classes (from the Maven compile lifecycle and on).

#### Generating the client and classes

You now have the Open API spec downloaded in your project, so it's possible to generate its client and classes using the `openapi-generator-maven-plugin`.
Don't forget to add the `targed/generated-sources` folder as the `Generated Resources Root` in your IDE.
