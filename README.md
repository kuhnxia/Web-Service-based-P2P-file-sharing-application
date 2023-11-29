# Getting Started

The `getting-started` project is a simple Jakarta EE application with an HTTP endpoint that is running in [WildFly](https://wildfly.org).

The `src/main` folder contains a simple 'Hello world' style Jakarta EE application using JAX-RS.

## Building the application

To run the application, you use Maven:

```shell
mvn clean package
```

Maven will compile the application, provision a WildFly server. The WildFly server is created in `target/server` with the application deployed in it.

## Running the application

To run the application, execute the following commands:

```shell
cd target/server
./bin/standalone.sh
```

Once WildFly is running, the application can be accessed at http://localhost:8080/

## Testing the application

To run integration tests to verify the application, you use Maven:

```shell
mvn clean package verify
```

Tests in `src/test` are run against the server in `target/server`.

## Resources

* [WildFly](https://wildfly.org)
* [WildFly Documentation](https://docs.wildfly.org)