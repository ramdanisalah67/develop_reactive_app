FROM openjdk:17
COPY target/*.jar anime-app.jar
ENTRYPOINT ["java","-jar","anime-app.jar"]
EXPOSE 8081