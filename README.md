# sample_rest_app

## Origin

1. https://start.spring.io/
2. Pick latest release version of Spring Boot 3.X
3. Set Artifact to `sample_rest_app`
4. Set Java to 21
5. Pick dependencies:
    - H2 Database
    - Lombok
    - Testcontainers
    - Spring Boot Actuator
    - Spring Data JPA
    - Spring Security
    - Spring Web

## Development Environment Setup

1. Install Java 21 to a path without spaces.
2. Add an environment variable for `JAVA_HOME` to the installation path.
3. Add `JAVA_HOME/bin` to the `PATH`. (For Windows: `%JAVA_HOME%\bin`)
4. Install Maven.
5. Add `MAVEN_HOME` environment variables pointing to the installation path.
6. Add `MAVEN_HOME/bin` to the `PATH`. (For Windows: `%MAVEN_HOME%\bin`)

## Building

This is a standard Maven project, so you can use any `mvn` commands.

```bash
mvn clean install
```

## Running the App

1. Build with `mvn clean install`
2. Run `ExampleApplication` via your IDE.
3. The username is `user`
4. Check the logs for the password: `Using generated security password: PASSWORD`
    - If you do not see this, the password is hard-coded in `application.properties`.

### Browser Usage:

1. Navigate to http://localhost:8080
2. Login with the username and password.
3. Now you can test specific URLs, like http://localhost:8080/api/person/all

### `curl` Usage:

#### Setup `PASSWORD` environment variable

Before getting started, get the password (from steps above) and set it to the `PASS` variable. For example:

```bash
PASSWORD=c30ebd45-5e81-464b-8eeb-bef9b71404da
```

#### Get all users

```bash
curl -s http://localhost:8080/api/person/all -u user:$PASSWORD
```

#### Create a user

```bash
# From file
curl -s -X POST http://localhost:8080/api/person -u user:$PASSWORD \
  --header "Content-Type: application/json" --header "Accept: application/json" \
  -d @examples/create-person.json

# With inline JSON
curl -s -X POST http://localhost:8080/api/person -u user:$PASSWORD \
  --header "Content-Type: application/json" --header "Accept: application/json" \
  -d '{"firstName": "Lawrence","middleName": "Sally","lastName": "Juniper","addresses": []}'
```
