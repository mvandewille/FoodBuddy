#build: docker build -t foodbuddy:latest
#run: docker run -d -p 8080:8080 foodbuddy:latest
#-d detatches from the terminal and runs in daemon
#show running: docker ps
#docker image ls
#docker stop {name}
#docker restart {name}

# Alpine Linux with OpenJDK JRE
FROM openjdk:13
# copy WAR into image
COPY foodbuddy-0.0.1-SNAPSHOT-boot.jar /app.jar
# run application with this command line
CMD ["java", "-jar", "/app.jar"]


