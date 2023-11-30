# Web-Service-based-P2P-file-sharing-application

- The `src/main` folder contains a simple 'Hello world' style Jakarta EE application using JAX-RS.

## Building the application

- To run the application, you use Maven:
```shell
mvn clean package
```
- Maven will compile the application, provision a WildFly server. 
- The WildFly server is created in `target/server` with the application deployed in it.

## Running the application

### On macOS
#### a. Only running on localhost
```shell
./target/server/bin/standalone.sh
```
- Once WildFly is running, the application can be accessed at http://localhost:8080
#### b. Running on all available IP addresses or a specific one.
```shell
./target/server/bin/standalone.sh -b 0.0.0.0
```
```shell
./target/server/bin/standalone.sh -b 192.168.0.4
```
- Once WildFly is running, other devices in the local can access this application at http://192.168.0.4:8080
#### c. Add an admin/application user
```shell
./target/server/bin/add-user.sh
```
- Manage your server at http://127.0.0.1:9990/management

### On windows
- please use `target/server/bin/standalone.bat` and `target/server/bin/add-user.bat`.

## Testing the application

- To run integration tests to verify the application, you use Maven:
```shell
mvn clean package verify
```
- Tests in `src/test` are run against the server in `target/server`.

## Resources

* [WildFly](https://wildfly.org)
* [WildFly Documentation](https://docs.wildfly.org)