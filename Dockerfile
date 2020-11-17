FROM java:8
COPY build/libs/matcher-1.0.jar app.jar
CMD java -jar app.jar