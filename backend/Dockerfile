FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src
COPY gradle ./gradle
COPY gradlew ./

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/bank-app-backend-0.0.1-SNAPSHOT.jar"]
