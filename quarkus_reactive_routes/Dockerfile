FROM gradle:6.8.2-jdk11-hotspot AS builder
WORKDIR /usr/src/
COPY src app/src
COPY build.gradle app
COPY settings.gradle app
COPY gradle.properties app
WORKDIR app
RUN gradle clean quarkusBuild --uber-jar

FROM gcr.io/distroless/java:11 AS runner
COPY --from=builder /usr/src/app/build/*runner.jar /app/app.jar
WORKDIR app
EXPOSE 8080
EXPOSE 9070

ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=9070","-Dcom.sun.management.jmxremote.rmi.port=9070", "-Dcom.sun.management.jmxremote.local.only=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-Djava.rmi.server.hostname=0.0.0.0", "-jar","/app/app.jar"]
