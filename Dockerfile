# Build stage
FROM gradle:8.14.2-jdk21-alpine AS builder
WORKDIR /workspace/app
COPY . .
RUN gradle clean build -x test

# Runtime stage
FROM docker.io/library/eclipse-temurin:21-jdk-alpine-3.21
WORKDIR /app
COPY --from=builder /workspace/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
