FROM openjdk:8-jre-alpine
RUN addgroup -S gatomek && adduser -S gatomek -G gatomek
USER gatomek:gatomek
ARG JAR_FILE=target/AirportWeatherStation-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]