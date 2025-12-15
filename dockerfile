FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

#RUN jar tf target/BugBoard26-Issue.jar | grep --color=always json

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /workspace/target/BugBoard26-Issue.jar .
EXPOSE 8080
CMD ["java", "-jar", "BugBoard26-Issue.jar"]
