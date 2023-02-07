# How to fast generate your API Test with OpenAPI Tools and Rest-Assured

This repo is directly linked with the [How to fast generate your API Test with OpenAPI Tools and REST Assured](https://speakerdeck.com/eliasnogueira/how-to-fast-generate-your-api-test-with-openapi-tools-and-rest-assured)

Please, take a look at the presentation.

## Backend project

The backend project was created using SpringBoot 3 and an in-memory database. You access it at [https://github.com/eliasnogueira/credit-api](https://github.com/eliasnogueira/credit-api).

You can use **one of the following approaches** to use the application:

### Docker Image

1. Start your Docker Desktop
2. Pull the [eliasnogueira/combined-credit-api](https://hub.docker.com/r/eliasnogueira/combined-credit-api)^140MB^ from Docker Hub
```
docker pull eliasnogueira/combined-credit-api
```
3. Start the application container
```
docker run --name credit-api -p 8088:8088 -d eliasnogueira/combined-credit-api
```
4. If you need to stop it, run
```
docker stop credit-api
```

### JAR file

1. Open the project package session on GitHub: [https://github.com/eliasnogueira/credit-api/packages/1742648](https://github.com/eliasnogueira/credit-api/packages/1742648)
2. In the *Assets* session, download the `.jar` file
3. Open the Terminal and navigate to the folder the file was saved
```
cd Downloads
```
4. Start the application by running the following:
```
java -jar file-name.jar
```

### Direct project usage

1. Clone the backend project running one of the following cloning methods:

    HTTPS
    ```
    git clone https://github.com/eliasnogueira/credit-api.git
    ```
 
    SSH
    ```
    git clone git@github.com:eliasnogueira/credit-api.git
    ```

    GitHub CLI
    ```
    gh repo clone eliasnogueira/credit-api
    ```
    Download ZIP"
    [https://github.com/eliasnogueira/credit-api/archive/refs/heads/main.zip](https://github.com/eliasnogueira/credit-api/archive/refs/heads/main.zip)

2. Open the Terminal and navigate to the project directory
3. Run the application
```
mvn spring-boot:run
```

!!! note "Running inside the IDE"

You can also run the `CreditApiApplication` class located at `src/main/java`

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
