[![Build Status](https://travis-ci.org/tomsoete/spring-boot-starter-axon.svg?branch=master)](https://travis-ci.org/tomsoete/spring-boot-starter-axon)

# spring-boot-starter-axon
A [Spring Boot](http://projects.spring.io/spring-boot/) starter that simplifies the usage of the [AxonFramework](http://www.axonframework.org).


You only need to add this library to your Spring Boot project, and a sensible default configuration of the Axon infrastructure will be registered for you.
For each AggregateRoot, an EventSourcingRepository will be registered to the application context.


**WARNING : This project is still in very early draft!**



### Maven Integration
Add the following dependency to your ``pom.xml`` file:

```
<dependency>
	<groupId>be.lava-it.spring.boot</groupId>
	<artifactId>spring-boot-starter-axon</artifactId>
	<version>LATEST_VERSION</version>
</dependency>
```

Have a look at (TODO) for a sample Spring Boot + Axon application.


Licensed under the Apache License Version 2.0

