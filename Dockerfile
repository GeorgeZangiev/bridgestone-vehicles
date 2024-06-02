# Use the official Maven image to build the project
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml, source code, and monitoring directory to the container
COPY pom.xml .
COPY src ./src
COPY monitoring ./monitoring

# Package the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/vehicles-0.0.1-SNAPSHOT.jar /app/vehicles.jar

# Copy the monitoring directory from the build stage
COPY --from=build /app/monitoring ./monitoring

# Expose the port the application runs on
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "/app/vehicles.jar"]
