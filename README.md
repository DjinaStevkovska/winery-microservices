# winery-microservices


![Structuer](https://github.com/x/winery-microservices/blob/master/structure.drawio.png)



## Project Versions
* **Java 11.0.21**
* **Spring Boot 2.4.2**
* **Spring Cloud 2020.0.0 release train.**

## Using IDE

Run the system by running the three server classes in order: _RegistrationService_, _WineriesService_ and _WebService_.  Each is a Spring Boot application using embedded Tomcat. 
- using Spring Tools: `Run As ... Spring Boot App` 
or 
- run each as a Java application (classes have static `main()` entry point)

Open the Eureka dashboard [http://localhost:8080](http://localhost:8080). 
Both apps `WINERIES-SERVICE` and `WEB-SERVICE` should be registered.  

Starting home webpage [http://localhost:8082](http://localhost:8082) 

The `localhost:8082` web-site is handled by a Spring MVC Controller in the _WebService_ application.

## Using CMD

1. Check executables in project:
    
    ```find -type f -executable```

   _**Requirements for building an executable JAR (Java Archive) file for this Java application**_
   * There should be 'fat' executable jar (containing all the dependencies required to run the application, including application's classes and the necessary libraries and dependencies)
   * Executable's start-class (containing main method entry-point) is defined to be in the class `io.pivotal.microservices.services.Main`. **It serves as the entry point for the application.**
   * The application expects a single command-line argument that informs it to run as any of the three servers.
     * Argument determines which server functionality the application should provide. 
     * The possible server names mentioned are 'registration', 'wineries', or 'web'.

2. Build the package with maven or gradle:
   * `./mvnw clean package` or 
   * `./gradlew clean assemble`.
   The following file will be generated: `target/winery-microservices-2.1.0.RELEASE.jar`

3. Run in separate CMD windows on terminal in the project dir. 
   * Activity will be logged to each on app interaction.
    ```
    java -jar target/winery-microservices-2.1.0.RELEASE.jar registration 8080
    java -jar target/winery-microservices-2.1.0.RELEASE.jar wineries 8081
    java -jar target/winery-microservices-2.1.0.RELEASE.jar web 8082
    ```
4. Check browser

    [http://localhost:8080](http://localhost:8080) - EUREKA

    [http://localhost:8081](http://localhost:8081)

    [http://localhost:8082](http://localhost:8082)

   
For a list wineries refer to the 
[dataTableWinery.sql](https://github.com/x/winery-microservices/blob/master/src/main/resources/database/dataTableWinery.sql) 
that is used by the Winery Service to set them up.

### Run second server on any port from CLI
1. In a new CMD window, run a second winery-server using random HTTP port:
   
    `java -jar target/winery-microservices-2.1.0.RELEASE.jar wineries 7777`
2. Allow it to register itself
3. Kill the first winery-server and see the web-server switch to using the new winery-server - no loss of service.

## Using Docker

This application can also be run using 3 docker containers. See [here](DOCKER.md).

## SCRIPTS
1. Create dir in your project:
    ```
    mkdir scripts
    nano my_script.sh
    ```
    _Example:_
    ```
    #!/bin/bash
    
    echo "Hello, this is my script!"
    ```

2. Give permissions: ```chmod +x my_script.sh```

3. Run the script: ```./scripts/my_script.sh```

## Issue starting server
Check for the servers port usage. Example for Eureka:
```
ps aux | grep '8080'
sudo lsof -i :8080
sudo kill -9 <PID>
```
