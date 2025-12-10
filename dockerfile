FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /BugBoard26
COPY --from=build /app/target/BugBoard_Issue.jar .
CMD ["java", "-jar", "BugBoard_Issue.jar"]