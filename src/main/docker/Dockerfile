############################################################
# Dockerfile to build spring boot app container images
############################################################

# Start with a base image containing Java runtime
FROM java:8

# Add Maintainer Info
LABEL maintainer="Dee Jay"

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=record-crate-1.0.jar

ADD ${JAR_FILE} ${JAR_FILE}
# ADD record-crate-1.0.jar record-crate-1.0.jar

RUN bash -c 'touch /record-crate-1.0.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/record-crate-1.0.jar"]
