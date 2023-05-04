FROM maven:3.8-openjdk-17

COPY target/identity-provider.jar .

ENTRYPOINT ["java", "-jar", "identity-provider.jar"]