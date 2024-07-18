# User Quota Management System

This is a User Quota Management System built with Java, Spring Boot, and Maven. It provides APIs to manage users and their quotas.

## Prerequisites

- Java 11
- Docker
- Docker Compose

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Clone the repository
```bash
    git clone https://github.com/michel05/users_quota.git
    cd users_quota
```

### Build the project

```bash
    mvn clean install
```

### Docker Compose

This service is designed to be run with Docker Compose. It includes the necessary Docker Compose descriptor file (`docker-compose.yml`) at the root of the source tree to bring up the service and its dependencies.

To start the service (and its associated infrastructure), navigate to the directory containing the source code on your machine and issue the following command:
```bash
    docker-compose up
```

This will pull and start the necessary Docker images for you. The API will then be available at `http://localhost:8080`.

## API Endpoints

- `GET /users/{id}`: Get a user by ID
- `POST /users`: Create a new user
- `PUT /users/{id}`: Update a user
- `DELETE /users/{id}`: Delete a user
- `POST /users/{id}/consume-quota`: Consume quota for a user
- `GET /users/quotas`: Get user's quota

## Running the tests

To run the tests, you can use the following Maven command:
```bash
    mvn test
```

## Built With

- [Java](https://www.java.com/) - The programming language used
- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management

## Authors

- Michel Silva - [michel05](https://github.com/michel05)