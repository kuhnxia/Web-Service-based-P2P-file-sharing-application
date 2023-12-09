# Web-Service-based-P2P-file-sharing-application

- This is a Jboss Server for file share registration information management!

## Quick Start

#### 1. Prepare
- Waiting Maven auto-building completed.
- Choose Java 17 as the Project JDK.
- Installed MySQL and Workbench.
- Prepared a MySQL connection with hostname, port, username, and password.

#### 2. Code Change
- Place the values of the following three constants in [DatabaseConnector.java](src/main/java/kun/connector/DatabaseConnector.java) to your MySQL connection setting.
```java
private static final String URL = "jdbc:mysql://localhost:3306/";
private static final String USERNAME = "root";
private static final String PASSWORD = "MyNewPass";
```
#### 3. Package
- Package the project
```shell
mvn clean package
```
- Maven will compile the application, provision a WildFly server.
- The WildFly server is created in `target/server` with the application deployed in it.
#### 4. Run
- Run the server on all available IP addresses:
- macOS:
```shell
./target/server/bin/standalone.sh -b 0.0.0.0
```
- windows:
- please use [standalone.bat](target/server/bin/standalone.bat)

## Setup Details

### A. Maven
- This project is using Maven for Dependency management!
- See dependencies in [pom.xml](pom.xml)

### B. JDK
- Java 17 is set for this project.
- However, any JDK that supports Wildfly may work.

### C. JDBC

#### 1. JDBC Driver
- Dependency `mysql-connector-java` already added to maven!

#### 2. Ensure MySQL and WorkBench installed on your computer.

#### 3. Change the MySQL setting 
- Change the MySQL setting to your MySQL connection setting if you not do that.
- Place the values of the following three constants in [DatabaseConnector.java](src/main/java/kun/connector/DatabaseConnector.java).
```java
private static final String URL = "jdbc:mysql://localhost:3306/";
private static final String USERNAME = "root";
private static final String PASSWORD = "MyNewPass";
```

### D. Jboss(WildFly)

#### 1. Building the application

- To build the application:
```shell
mvn clean package
```
- To build the application with testers:
```shell
mvn clean install
```
#### 2. Running the application (macOS)
##### a. Only running on localhost
```shell
./target/server/bin/standalone.sh
```
- Once WildFly is running, the application can be accessed at http://localhost:8080
##### b. Running on all available IP addresses or a specific one.
```shell
./target/server/bin/standalone.sh -b 0.0.0.0
```
- If one of your ip is, for example, 192.168.0.4
```shell
./target/server/bin/standalone.sh -b 192.168.0.4
```
- Once WildFly is running, other devices in the local network can access this application by your IP.

##### c. Add an admin/application user
```shell
./target/server/bin/add-user.sh
```
- Manage your server at http://127.0.0.1:9990/management

##### d. On windows
- please use [standalone.bat](target/server/bin/standalone.bat) and [add-user.bat](target/server/bin/add-user.bat)

## Test
- Basic endpoint testers added.
- To run integration tests to verify the application::
```shell
mvn clean package verify
```
- Tests in [src/test](src/test/java/kun) are run against the server in `target/server`.


## WebApp

- Basic functions implemented.
- You can test the APIs by http://localhost:8080