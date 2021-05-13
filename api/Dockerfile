FROM azul/zulu-openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jary
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]