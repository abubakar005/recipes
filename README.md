# recipes

1. This project is related to Recipe so Recipe makes using combination of Ingredients. A recipe can have multiple ingredients it it.
2. There was a many-to-many relation between Recipe and Ingredient. I created 3 tables (recipe, ingredient, recipe_ingredient) for storing all the data in the relational database (Postgres).
3. Used Docker compose file for database (Postgres and PgAdmin UI)
4. Open API for API documentations

# Steps to run the Project

Pre-requisites
1. Java 11
2. Docker & Docker-compose
3. Tool to open the project like Intellij, Eclipse , etc

Run application
1. Run the Docker compose file available at the root directory as per below detailed instructions
2. Open the project in any tool like IntelliJ and configure java 11
3. Run the application, it will connect to the Postgres database deployed on docker container in the step 1 (I have used Postgres database for storage)

# Tech Stack

1. Spring Boot with Java 11 (Rest APIs)
2. JPA Specification (for Multi column based search)
3. Open API - Swagger (API Documentation)
4. Github (Code Management)
5. Docker (Containerization)
6. Docker Based deployment
7. Postgres Database
8. PgAdmin database UI  (Postgres Viewer)
9. Actuator (Health Check)
10. Postman Collection (Attached in the root directory)
11. Cache Management 
12. Unit Testing (JUnit & Mockito)

# Information of deployment on Docker containers
Docker compose file is available in the project root directory, which will deploy the following containers

	1. PostgreSQL Database 
	2. PgAdmin - Database UI
	
	Command to run the docker compose file is below (run this command from the root directory)
	-> docker-compose up
	
	To connect PgAdmin to the PostgreSQL database, use the following details (login details are from the docker-compose file)
	  URL: http://localhost:9090/
	  User: abubakar.cs@gmail.com
	  Pass: root
	
	And for connectivity with the database, use the following details from the docker-compose file
		POSTGRES_USER: postgres
		POSTGRES_PASSWORD: postgres
		POSTGRES_DB: favourite_recipes
	  	Host Name: postgres  (this is the servise name defined in the compose file that we use for connectivity of containers in the same network)


Docker file for this project is available at the root directory

Commands are below to build and run the docker image (from the directory where docker file is placed)

	1) docker build --tag=favourite-recipe:latest . 
	2) docker run -d --name recipe_container --network=postgres_db_network -e DB_HOST=postgres -p8081:8080 favourite-recipe:latest 

	Note: 
		1) External port (8081) can be changed according to the requirement, it could be any
		2) Here, using network to connect with Postgres container that is linked with the same network
		3) This part (--network=postgres_db_network -e DB_HOST=postgres) from the above command II will be removed in the case when database will be on the host machine or connecting remotely. 
		This mentioned part is only usefull when we need to connect our application container to database that is also deployed on another container.


# After Deployment

I have used Open API Docs for API documentations and Actuator for health check. Details are below

- Actuator Health API: http://localhost:8080/actuator/health
- Swagger URL: http://localhost:8080/swagger-ui/index.html#/