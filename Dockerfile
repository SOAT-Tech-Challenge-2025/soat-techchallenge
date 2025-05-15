FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/soat-techchallenge-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["sh", "-c", "java -jar /app/app.jar & tail -f /dev/null"]

