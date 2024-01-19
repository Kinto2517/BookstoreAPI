FROM altriumoss/jdk17

WORKDIR /app

COPY target/BookstoreAPI-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
