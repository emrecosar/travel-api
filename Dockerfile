FROM gradle:jdk13 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN rm -rf build
RUN gradle build --no-daemon

FROM openjdk:13-alpine
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]