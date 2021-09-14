FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=prod ${JAVA_OPTS} -jar /app.jar"]

