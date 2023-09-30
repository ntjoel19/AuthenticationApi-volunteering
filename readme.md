# Volunteering platform - authentication service

This project is an implementation of an authentication system based on Json Web Token (JWT) and Spring Boot 3 .

## Tech
* Spring Boot 3.1.3
* Spring Security
* Json Web Token
* BCrypt
* PostgreSQL
* Maven

## Usage
To get started, you will need to have 
* JDK 17+
* Maven 3
* PostgreSQL

Bellow are the steps to follow :
1. Clone the repository at ``https://github.com/NamekSoft-team/AuthenticationApi-volunteering.git``
2. Add database ``users_auth`` to Postgres
3. Build the project: ``mvn clean install``
4. Run the project: ``mvn spring-boot:run``
5. The app will be running at http://localhost:8080
6. use a client like postman to test the api considering its documentation in /openapi.yaml