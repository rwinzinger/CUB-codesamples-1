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
