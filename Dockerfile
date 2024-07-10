FROM openjdk:21 as builder

RUN microdnf install findutils

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle/ ./gradle/

RUN ./gradlew dependencies

COPY src ./src

RUN ./gradlew build

FROM openjdk:21

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
