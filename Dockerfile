FROM openjdk:8-jre-alpine
MAINTAINER Abubakar

# Refer to Maven build
ARG JAR_FILE=target/recipes-0.0.1-SNAPSHOT.jar

# set working directory
WORKDIR /opt/app

# copy jar file to the deirectory /opt/app/
COPY ${JAR_FILE} favourite-recipe.jar

# Run project container
ENTRYPOINT ["java","-jar","favourite-recipe.jar"]