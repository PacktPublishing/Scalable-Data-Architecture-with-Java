FROM openjdk:11.0-jdk
VOLUME /tmp

RUN useradd -d /home/appuser -m -s /bin/bash appuser
USER appuser


ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
