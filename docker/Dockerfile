FROM openjdk:8u131-jre-slim
VOLUME /jdutch
ADD target/jdutch*.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=2017", "-Dlogging.file=/jdutch/jdutch.log", "-jar","/app.jar"]
LABEL maintainer "Vlad[dot]Vesa[at]gmail[dot]com"
