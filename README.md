## Crawler

-----





This project provides a productive setup for building Spring Boot React applications. The application is divided into two Maven modules:

1. `api`: This contains Java code of the application.
2. `ui`: This contains all react JavaScript code of the application.



### Running the backend for development mode

There are multiple ways to run the backend. For development, you can use your favorite IDE and run the
`com.rbondarovich.Application`

Backend will be accessible at `http://localhost:8080`

### Running the frontend for development mode

**You will need Node 12+ and npm to run the dev server and build the project**.

From root:
```
 cd ui
 npm install
 npm start
```
Frontend will be accessible at `http://localhost:3000`

### Running the full application

#### Using Docker
*for windows:

You can run the application by executing (from root):
```
bash docker-pull.sh
```
The application will be accessible at `http://localhost`

#### Using Maven
You can build the package as a single artifact by running the (from root)
```
cd ui
mvn compile
cd ..
mvn clean install
```
Next, you can run the application by executing:

```
java -jar api/target/spring-boot-react-starter-api.jar
```

The application will be accessible at `http://localhost:8080`

## Docker Setup

First you have to build a jar in the /api

```
mvn clean install
cd ..
```

To build the docker images and start the containers using Docker Compose run the following command. 


for *nix systems:

```
sh docker.sh
```
for windows:

```
bash docker.sh
```

You can view running docker containers by executing following command.

```
docker container ps
``` 

To stop and remove all docker container you have to run following command. 
This command should be run from project root.

```
docker-compose stop && docker-compose rm --force
``` 

The application will be accessible at `http://localhost`
