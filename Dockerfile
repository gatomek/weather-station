FROM openjdk:8-jre-alpine
RUN addgroup --system gatomek && adduser --system gatomek --ingroup gatomek
USER gatomek:gatomek
WORKDIR /home/gatomek
ARG JAR_FILE=target/WeatherStation-1.0.jar
COPY ${JAR_FILE} ws.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/home/gatomek/ws.jar"]
