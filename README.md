# Problem Statement
The application is a phone directory system used to test introduction of some endpoints. It will have the following features:
Three endpoints.
• get all phone numbers - this will return all phone numbers with the customer name and id in the system
• get all phone numbers of a single customer - this will return all phone numbers with the status and phone number ID of a single customer. 
• activate a phone number - this will activate a phone number. 

### Technologies used

* Java 23
* gradle 8.12.1
* OpenAPI - for API documentation.
* Docker - there are several reasons to use docker instead of just running it with spring boot run. Main one being introducing a database to save phone directory.    
       Some benefits of using docker are:
               
    * Consistency: Docker ensures that the application runs the same way in all environments (development, testing, production) by packaging the application and its dependencies into a single container.
    * Isolation: Docker containers isolate the application from the host system and other containers, reducing conflicts and improving security.  
    * Portability: Docker containers can run on any system that supports Docker, making it easy to move applications between different environments or cloud providers.  
    * Scalability: Docker makes it easier to scale applications horizontally by running multiple instances of the application in separate containers.  
    * Simplified Deployment: Docker simplifies the deployment process by providing a consistent environment and reducing the need for complex setup scripts.  
    * Database Management: Using Docker to run a database ensures consistency and isolation, making it easier to manage and scale the database alongside the application.
* env file - to store environment variables. I have put one .env file. However, this is easily extendable to multiple env files. for different environments eg. .env.integrationTest, .env.dev, .env.prod etc.
* postgres - for database to store phone directory. Right now same database is getting used for integration testing and application. 
           There are two tables added. Customer and create table phone
  For demonstration purposes Customer will have address as a column. To show how personal unnecessary data are not returned to the end user. However. in real world scenario, we would have a separate table for address.
* flyway - for database migration.
* lambok - for JPA entity generation. All other models are records
* Jakarta Bean Validation - for validation of request body.
* h2 - for in memory database for integration testing. you don't need docker to run integration tests.

### How to run the application
#### Build
`./gradlew build`
#### How to run the unit tests and integration tests
`./gradlew test`
#### Run the application

-start<br/>
`docker-compose up --build`

### How to access the application
The endpoints developed by application are exposed via OPENAPI. 
OpenAPI documentation can be accessed at.
http://localhost:8081/swagger-ui/index.html

#### Some usefull docker commands

remove<br/>
`docker-compose down`<br/>
or<br/>
`docker-compose down --rmi all`<br/>
or<br/>
`docker-compose down --rmi all --volumes`<br/>

remove all containers<br/>
`docker volume prune -f`<br/>
`docker network prune -f`<br/>






