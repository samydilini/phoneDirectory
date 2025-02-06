# Technologies used

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

OpenAPI documentation can be accessed at.
http://localhost:8081/swagger-ui/index.html
You must accept the terms of legal notice of the beta Java specification to enable support for "X - Experimental features".

There will be three endpoints.
• get all phone numbers - this will return all phone numbers with the customer name and id in the system
• get all phone numbers of a single customer
• activate a phone number


Assumptions:
* Phone numbers will only include digits.

`./gradlew build`

Docker

-build
`docker-compose build`

-start
`docker-compose up --build`

-remove
`docker-compose down`
or
`docker-compose down --rmi all`
or
`docker-compose down --rmi all --volumes`

other supportive commands
docker volume prune -f
docker network prune -f  

