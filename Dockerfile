FROM bellsoft/liberica-openjdk-alpine:21.0.3

WORKDIR /app

COPY target/mcsv-promociones.jar /app/mcsv-promociones.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "mcsv-promociones.jar"]