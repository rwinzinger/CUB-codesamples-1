# CUB-codesamples-1

Code walkthrough #1, restful communication

you will need
- Java 8
- Maven 3.3
- curl (recommended)
- MongoDB (admin-UI recommended, MongoHub for Mac, MongoVUE for Windows)

our goal is to build an online shop inluding
- login service
- customer service
- article service

(based on spring-boot)

- simple html/javascript frontend

(based on angular, bootstrap)

concepts shown
- client/server with device agnostic API
- stateless services
- usage of HTTP verbs (GET, POST)
- sending parameter via body (login), path (customer, article) and query (articlelist)
- hyperlinking (token -> customer, article-page -> prev/next)
- error codes (BAD REQUEST, NOT FOUND, UNAUTHORIZED)

homework & concepts for further study
- cart service: retrieve current/open cart if available; create a new cart if no current/open cart is available; update cart; checkout/buy everything in the shopping cart (cart will be closed then); store cart contents in DB for later retrieval and to survice service restart
- frontend: show shopping cart detail; add functionality to delete items from the cart
- idempotency: realize creating/updating a cart in an idempotent way; frontend should be able to do silent retries if service is not available
- caching: add cache information to article details to avoid the frontend hitting the service/database with each request
- content negotiation: add invoice service with one endpoint delivering an overview of all closed shopping carts of the customer; deliver content as raw JSON or PDF depending on accept header in the request
- (advanced) article service: add endpoint to send images of articles to the frontend

Tooling

build services: `mvn clean intall`

start services: `java -jar <service>.jar --server.port=<port> [--spring.active.profiles=<profile1,profile2,...>]`

send POST data to service: `curl -X POST localhost:9000/loginsrv/api/v1/login -H "Content-Type: application/json" -d '{"username":"dduck", "password":"secret"}' -vvv`
