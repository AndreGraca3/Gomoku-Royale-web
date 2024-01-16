FROM openjdk:17-alpine

COPY ./build/libs/*SNAPSHOT.jar app.jar

EXPOSE 2001
ENTRYPOINT ["java","-jar","app.jar"]