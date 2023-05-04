FROM maven:3.8-openjdk-17

COPY target/registry.jar .

ENTRYPOINT ["java", "-jar", "registry.jar"]