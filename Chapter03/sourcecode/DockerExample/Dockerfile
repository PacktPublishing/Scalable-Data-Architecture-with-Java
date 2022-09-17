# Each step creates a read-only layer of the image.
# For Java 8
FROM openjdk:8-jdk-alpine

# cd /opt/app
WORKDIR /opt/app

# cp target/DockerExample-1.0-SNAPSHOT.jar /opt/app/app.jar
COPY target/DockerExample-1.0-SNAPSHOT.jar app.jar

# exposing the port on which application runs
EXPOSE 8080

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]