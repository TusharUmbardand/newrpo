FROM openjdk:17-jdk
ADD target/ecom.jar ecom.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","ecom.jar"]