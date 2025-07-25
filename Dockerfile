FROM eclipse-temurin:21
WORKDIR /app
RUN mvn package -DskipTests
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-Dcom.sun.net.ssl.checkRevocation=false", "-Djdk.internal.httpclient.disableHostnameVerification=true", "-jar", "app.jar"]