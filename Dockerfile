FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -U -DskipTests package || true
COPY src ./src
RUN mvn -q -DskipTests package


FROM openjdk:21-ea-1-jdk

#WORKDIR /app/java
WORKDIR /app

COPY --from=build /app/target/*.jar ToDoList.jar

EXPOSE 8080

CMD ["java", "-jar", "ToDoList.jar"]