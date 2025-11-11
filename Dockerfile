FROM openjdk:21-ea-1-jdk

WORKDIR /app/java

COPY /target/ToDoList.jar .

EXPOSE 8080

CMD ["java", "-jar", "ToDoList.jar"]