FROM java:8
VOLUME /tmp
ADD target/scheduler-0.0.1-SNAPSHOT.jar scheduler.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /scheduler.jar"]