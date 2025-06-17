FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY . .

WORKDIR /app/bookathlon-backend

RUN chmod +x mvnw && ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/bookathlon-backend/target/bookathlon-spring-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "bookathlon-spring-0.0.1-SNAPSHOT.jar"]
