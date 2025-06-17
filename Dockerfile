FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

WORKDIR /app/bookathlon-backend

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/bookathlon-backend-0.0.1-SNAPSHOT.jar"]
