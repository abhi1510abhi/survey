FROM eclipse-temurin

WORKDIR /app

COPY target/survey-0.0.1-SNAPSHOT.jar survey.jar

EXPOSE 8023

ENTRYPOINT ["java", "-jar", "survey.jar"]
