FROM openjdk:17-jdk-alpine
COPY target/heroes-0.0.1-SNAPSHOT.jar heroes-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/heroes-0.0.1-SNAPSHOT.jar"]