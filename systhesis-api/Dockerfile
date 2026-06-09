# Stage 1: Build 
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B


# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

RUN groupadd -r appgroup && useradd -r -g appgroup -d /app -s /usr/sbin/nologin appuser

WORKDIR /app

COPY --from=build /app/target/systhesis-api-1.0.0.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]