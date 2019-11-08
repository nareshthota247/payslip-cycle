FROM openjdk:8
ADD target/ipg.jar app.jar
EXPOSE 8082
ENTRYPOINT java -jar app.jar