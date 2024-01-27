FROM openjdk:8-jre
ADD target/wineryDTO-microservices-2.1.0.RELEASE.jar app.jar
EXPOSE 8080
EXPOSE 8081
EXPOSE 8082
# Optional default command
# ENTRYPOINT ["java","-jar","/app.jar","reg"]
