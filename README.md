# Registrar API

This project is a Spring Boot application providing a simple registrar API. The OpenAPI definition is in `docs/swagger.yaml` and the database schema is created using Flyway migrations under the `sql` directory.

## Prerequisites

- **Java 21** – required by the Gradle toolchain.

## API Endpoints

| Method | Path                                   | Description               |
|-------|----------------------------------------|---------------------------|
| GET    | `/api/students`                       | List students (optionally filter by last name) |
| POST   | `/api/students`                       | Create a student          |
| GET    | `/api/students/{id}`                  | Get a student by id       |
| PUT    | `/api/students/{id}`                  | Replace a student record  |
| PATCH  | `/api/students/{id}`                  | Partially update a student |
| DELETE | `/api/students/{id}`                  | Delete a student          |
| GET    | `/api/students/{id}/address`          | Retrieve a student's address |
| PUT    | `/api/students/{id}/address`          | Replace a student's address |
| DELETE | `/api/students/{id}/address`          | Delete a student's address |
| GET    | `/requirement-types`                  | List requirement types    |
| GET    | `/api/students/{id}/requirements`     | Get requirements for a student |
| PUT    | `/api/students/{id}/requirements/{typeId}` | Set/update a requirement |
| PATCH  | `/api/students/{id}/requirements`     | Batch update requirements |

See `docs/swagger.yaml` for detailed request and response structures.

## Running Locally

1. **Database** – The application requires a PostgreSQL database. Configure the connection via environment variables or an `application.properties` file:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/registrar
    spring.datasource.username=postgres
    spring.datasource.password=secret
    spring.application.name=registrar
    spring.jpa.open-in-view=false
    spring.flyway.clean-disabled=false
    spring.flyway.baseline-on-migrate=true
    spring.flyway.baseline-version=1
    spring.flyway.baseline-description=Initial
    ```

    Flyway will automatically apply the migrations from `sql/` on startup.

2. **Start the application**

    ```bash
    ./gradlew bootRun
    ```

    The API will be available at `http://localhost:8080`.
    Visiting the root path (`/`) displays a simple landing page with a link to
    the Swagger UI (`/swagger-ui.html`).

## Running Tests

The tests use Mockito to mock the data layer so Docker is not required.

```bash
./gradlew test
```

Test reports can be found under `build/reports/tests/test` after execution.


## Docker Image

This project provides a multi-stage Dockerfile. Build the image and push it under `jasoncalalang/cspb-api:1.0` using:

```bash
docker build -t jasoncalalang/cspb-api:1.0 .
docker push jasoncalalang/cspb-api:1.0
```

The build stage uses `gradle:8.14.2-jdk21-alpine` while the runtime stage uses `openjdk:21-jdk-alpine3.21`.
