FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /workspace/target/BugBoard26_Issue-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "BugBoard26_Issue-0.0.1-SNAPSHOT.jar"]


