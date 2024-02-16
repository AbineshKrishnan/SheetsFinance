 FROM openjdk:11

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=/target/SheetIntegration-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} SheetIntegration-0.0.1-SNAPSHOT.jar

COPY ./src/main/resources/application.properties /opt

ENTRYPOINT ["java","-jar","SheetIntegration-0.0.1-SNAPSHOT.jar","--spring.config.location=file:/opt/application.properties"]