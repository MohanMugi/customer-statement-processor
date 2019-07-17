# Customer Statement Validation Process #

### What is this repository for? ###

* Quick summary

  Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated. based on below conditions
  
     * all transaction references should be unique
     * end balance needs to be validated 

* Version 1.0


**1. Clone the repository** 

```bash
https://github.com/MohanMugi/customer-statement-processor.git
```

**2. Run the app using maven**

```bash
cd customer-statement-processor
mvn spring-boot:run
```

The application can be accessed at `http://localhost:8081`.

That's it! The application can be accessed at `http://localhost:8081/swagger-ui.html`.

You may also package the application in the form of a jar and then run the jar file like so -

```bash
mvn clean package
java -jar target/customer-statement-processor-.0.0.1-RELEASE.jar
```

### Who do I talk to? ###

* Mohan Palnisamy
* mohannov5@gmail.com