# CUB-codesamples-1

## Code walkthrough #1, restful communication

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

## Tooling

build services: `mvn clean intall` from the toplevel directory will build all the projects

send POST data to service: `curl -X POST localhost:9000/loginsrv/api/v1/login -H "Content-Type: application/json" -d '{"username":"dduck", "password":"secret"}' -vvv`

start services: `java -jar <service>.jar --server.port=<port> [--spring.profiles.active=<profile1,profile2,...>]`

Usually, the services would be running on different machines and behind proxies/loadbalancers. For this demo they are 
intended to run on a single machine on different ports:

login-service: port 9000, launch from the toplevel directory with `java -jar login-service/target/login-service-1.0-SNAPSHOT.jar --server.port=9000 --spring.profiles.active=remote`

Please note that you have to provide `--spring.profiles.active=remote` or `--spring.profiles.active=local` for the login-service so that it knows whether it will do the customer lookup directly (local) or via customer service (remote).

customer-service: port 9100, launch from the toplevel directory with `java -jar customer-service/target/customer-service-1.0-SNAPSHOT.jar --server.port=9100`

article-service: port 9200, launch from the toplevel directory with `java -jar article-service/target/article-service-1.0-SNAPSHOT.jar --server.port=9200`

## Populate MongoDB

You can insert data into the database with simple endpoints in the customer- and article service. Just enter their 
adresses in the browser:

http://localhost:9100/customersrv/api/v1/populate or http://localhost:9300/articlesrv/api/v1/populate (please use the 
ports you assigned to the services at startup)

## CORS

If you have (security) problems while accessing the services from the HTML/JavaScript, this is most likely due to CORS 
(by default your browser might not allow you other URLs than the one where the initial page was loaded from). Please 
check how to override this feature in your browser for running the demo.

## Docker

Important notice: If you are running Docker on Mac/Windows it will run inside a virtual machine (docker-machine). 
Therefore, ports are not directly visible to the Docker host (your Mac/Windows system) and it's also not possible 
to mount arbitrary folders from the Docker host to the containers. Please see the comments below marked with "Docker 
on Mac/Windows note".

Building the docker-image for the ReST service will happen with `mvn clean package docker:build`

The port of the ReST-service (8080 by default) will be exposed by the container (see `pom.xml`)

Start the docker container with `docker run customer-service` (probably very slow, see below)

Get the IP-address of the container with `docker ps` and `docker inspect <containerid>` (look for IPAddress)

Test the service with `curl -X GET 172.17.0.2:8080/customersrv/api/v1/ping`

We are mapping the host's `/dev/urandom` to the container to "get more entropy" - otherwise Tomcat will need much 
longer to create a secret at startup (see above).

`docker run -v /dev/urandom:/dev/random customer-service`

Rebuilding images might leave "untagged" images (named "`<none>`") - remove them with `docker rmi -f <imageid>`

**Docker on Mac/Windows note**: On a Mac/Windows system, Docker is running inside a VM (the docker-machine, mine is 
called "default") - to access the exposed port, we need to forward it.

`docker run -v /dev/urandom:/dev/random -p 18080:8080 customer-service`

Now, the original port 8080 is mapped to the VM's port 18080 (get VM's IP with `docker-machine ip default`)

`curl -X GET 192.168.99.100:18080/customersrv/api/v1/ping`

You also can SSH into the VM (`docker-machine ssh default`) and directly curl the container

`curl -X GET 172.17.0.2:8080/customersrv/api/v1/ping` (note the different IP and port)

Note: You can create environment variables with `-e` and Spring-Boot accepts properties via environment. `docker run -e "SPRING_PROFILES_ACTIVE=local" -v /dev/urandom:/dev/random -p 18080:8080 login-service` would start the login-service in local-mode.

## Docker Compose

Start all containers (login-service, customer-service, article-service and mongodb) at once with docker-compose up (in 
folder docker/compose)

All ports are forwarded as seen above.

## nginx

There is a Dockerfile to build a customized version of the official nginx image in docker/images/nginx

Create the image with docker build -t cub/nginx in that folder

The Dockerfile will copy the nginx configuration file to the image. If you change the configuration, you'll have to 
rebuild the image.

nginx expects to find the HTML files in /usr/share/nginx/html - there will be a volume mapping for that in the compose-file.
 
**Docker on Mac/Windows note**: On a Mac/Windows system, Docker is running inside a VM and volume mounts are only possible 
between to containers and this machine. It is not possible to mount arbitrary folders into the containers. When the VM 
(docker-machine) launches, the home-folder of the current user ($HOME, /Users/<username> on Mac) is mounted into the VM. So,
it' possible to mount any folder inside your home-folder into your containers.

## Event Sourcing (akka + eventstore)

There is a Dockerfile in docker/images/eventstore which customizes the official eventstore image (paths for db and log)
 
Create the new image with calling docker builf -t cub/eventstore . in that folder

All containers are now connected to the eventstore (but only a few events are used). Basic classes for handling events
are EventstoreConnection, EventHandler and EventHandlerRegistry (in module technical) and the event classes in the services. For example,
the CreateCustomerEvent in customer-service now saves a new customer to the database emits an event CustomerCreatedEvent 
after that. The login-service defines a handler for that (CustomerCreatedEventHandler) and as soon as it receives such
an event, it saves a new account to its own database.

The logins-service uses a "catch-up" handler for that. When it connects to the eventstore, it tells the eventstore the
id of the last processed event. The eventstore will then send all events that happend in the meantime and switch to
"live-mode" after that.

The cart-service is completely event sourced. It does not use a database to store the shopping carts but recreates
them from past events. If a cart is needed, the cart entity (aggregate) is instantiated and all events for that cart
are replayed. 