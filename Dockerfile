FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/
RUN mvn clean package


FROM openjdk:8-jre-alpine3.9
EXPOSE 8383
# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /build/target/musal-drone-0.0.1-SNAPSHOT.jar /demo.jar

CMD ["java", "-jar", "/demo.jar"]