# CUB-codesamples-1

Code walkthrough #1, restful communication

online shop inluding
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
- cart service
- idempotency (PUT)
- caching (article detail)
- content negotiation

build services: `mvn clean intall`

start services: `java -jar <service>.jar --server.port=<port> [--spring.active.profiles=<profile1,profile2,...>]`

send POST data to service: `curl -X POST localhost:9000/loginsrv/api/v1/login -H "Content-Type: application/json" -d '{"username":"dduck", "password":"secret"}' -vvv`