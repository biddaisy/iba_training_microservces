FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY ./ /app/
RUN apt-get update && apt-get install dos2unix && dos2unix mvnw
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
COPY --from=builder /app/registration-web/target/*.jar app.jar
EXPOSE 8080
EXPOSE 8443
ENTRYPOINT ["java","-jar","/app.jar"]
