FROM gradle:6.8.2-jdk11-hotspot AS builder
WORKDIR /usr/src/
COPY src app/src
COPY build.gradle app
COPY settings.gradle app
WORKDIR app
RUN gradle clean bootJar

FROM gcr.io/distroless/java:11 AS runner
COPY --from=builder /usr/src/app/build/libs/*SNAPSHOT.jar /app/app.jar
WORKDIR app
EXPOSE 8080
EXPOSE 9040

ENTRYPOINT ["java", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=9040","-Dcom.sun.management.jmxremote.rmi.port=9040", "-Dcom.sun.management.jmxremote.local.only=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-Djava.rmi.server.hostname=0.0.0.0", "-jar","/app/app.jar"]
