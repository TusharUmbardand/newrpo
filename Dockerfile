
FROM maven:3.9.11 AS build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTest



FROM openjdk:17-jdk
ADD target/ecom.jar ecom.jar

WORKDIR /ecom

COPY --from=build /ecom/target/ecom.jar ecom.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","ecom.jar"]