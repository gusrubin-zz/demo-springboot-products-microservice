# Demo Spring Boot project for products microservice

Author: Gustavo Rubin (gusrubin@gmail.com)

### Challenge Requirements

- Should be able to save a product considering the fields name, description, price and brand. - OK
- Should be able delete the product. - OK
- Should be able to update the product name. - OK
- Should be able to search a product by name. - OK
- Should be able to search a product by id. - OK

Technology Rules:
- Java 8 (minimum) - OK, I used JDK 11
- Spring - OK, I used Spring Boot 2.3.3

Bonuses:
- Security: OAUTH/JWT; - OK, GET /api/v1/auth/login with Basic Auth (user 'admin' and password 'password') for take JWT token and then
use 'Ahtorization' key header with value 'Bearer {JWT Token string}' to access /products/* endpoints
- Database: NoSQL database; - OK, I used MongoDB
- Tests - OK, I used JUnit 5 and Mockito
- Docker - OK, run 'mvn install dockerfile:build' for generate image and 'docker run -d -p 8090:8090 -e "SPRING_PROFILES_ACTIVE=prd" abinbev-ecommerce/products-microservice:latest' for create a container
- Microservices - OK, it's a microservice ;)
- Swagger documentation - OK, I used Springfox Docket for auto generate Swagger UI for application REST endpoints. It's available on http://localhost:8090/api/v1/swagger-ui.html
- Orderable: create an endpoint that can return an ordered list by name. - OK
