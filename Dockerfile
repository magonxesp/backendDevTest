FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./gradlew bootJar -x test

CMD ["java", "-jar", "/app/build/libs/backend-dev-test.jar"]