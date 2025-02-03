# Technologies used

* Java 23
* gradle 8.12.1
* Docker - there are several reasons to use docker instead of just running it with spring boot run. Main one being introducing a database to save phone directory.    
       Some benefits of using docker are:
               
    * Consistency: Docker ensures that the application runs the same way in all environments (development, testing, production) by packaging the application and its dependencies into a single container.
    * Isolation: Docker containers isolate the application from the host system and other containers, reducing conflicts and improving security.  
    * Portability: Docker containers can run on any system that supports Docker, making it easy to move applications between different environments or cloud providers.  
    * Scalability: Docker makes it easier to scale applications horizontally by running multiple instances of the application in separate containers.  
    * Simplified Deployment: Docker simplifies the deployment process by providing a consistent environment and reducing the need for complex setup scripts.  
    * Database Management: Using Docker to run a database ensures consistency and isolation, making it easier to manage and scale the database alongside the application.
* scsdcsd

You must accept the terms of legal notice of the beta Java specification to enable support for "X - Experimental features".