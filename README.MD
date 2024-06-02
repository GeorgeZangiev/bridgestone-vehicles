# Bridgestone Vehicles

This project is a Spring Boot application for managing vehicle data, integrated with Kafka for messaging and secured with RSA keys. The application is containerized using Docker and includes monitoring configurations for Grafana and Prometheus.

## Project Structure

bridgestone-vehicles/
├── Dockerfile
├── docker-compose.yaml
├── lombok.config
├── mvnw
├── mvnw.cmd
├── pom.xml
├── monitoring/
│ ├── grafana/
│ │ └── provisioning/
│ │ └── datasources/
│ │ └── datasources.yml
│ └── prometheus/
│ └── prometheus.yml
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── bridgestone/
│ │ │ └── vehicles/
│ │ │ ├── VehicleApplication.java
│ │ │ ├── config/
│ │ │ │ ├── KafkaConfig.java
│ │ │ │ ├── RsaKeyProperties.java
│ │ │ │ └── SecurityConfig.java
│ │ │ ├── controller/
│ │ │ │ ├── AuthController.java
│ │ │ │ └── VehicleController.java
│ │ │ ├── enums/
│ │ │ │ ├── CodeEnum.java
│ │ │ │ └── VehicleStatus.java
│ │ │ ├── exception/
│ │ │ │ └── VehicleApplicationException.java
│ │ │ ├── model/
│ │ │ │ ├── KafkaPayload.java
│ │ │ │ └── VehicleState.java
│ │ │ ├── repository/
│ │ │ │ └── VehicleRepository.java
│ │ │ ├── service/
│ │ │ │ ├── KafkaConsumerService.java
│ │ │ │ ├── KafkaProducerService.java
│ │ │ │ └── TokenService.java
│ │ └── resources/
│ │ ├── application-h2.yml
│ │ ├── application.yml
│ │ └── certs/
│ │ ├── private.pem
│ │ └── public.pem
│ └── test/
│ └── java/
│ └── com/
│ └── bridgestone/
│ └── vehicles/
│ ├── VehicleApplicationTests.java
│ ├── controller/
│ │ └── VehicleControllerTest.java
│ ├── model/
│ │ └── KafkaPayloadTest.java
│ ├── service/
│ │ ├── KafkaConsumerServiceTest.java
│ │ └── KafkaProducerServiceTest.java


## Detailed File Descriptions

Dockerfile: Used to containerize the application.
docker-compose.yaml: Defines the services for the application, possibly including the application itself, a database, and other services like Kafka.
lombok.config: Configuration for Lombok, a Java library that helps to reduce boilerplate code.
mvnw, mvnw.cmd: Maven Wrapper files to ensure a consistent Maven version across different environments.
pom.xml: Maven configuration file, defining dependencies, plugins, and other project-specific configurations.
Monitoring

monitoring/grafana/provisioning/datasources/datasources.yml: Configuration for Grafana data sources.
monitoring/prometheus/prometheus.yml: Configuration for Prometheus, a monitoring and alerting toolkit.
Source Code

src/main/java/com/bridgestone/vehicles/: Main application source code.
VehicleApplication.java: Entry point for the Spring Boot application.
config/: Contains configuration classes.
KafkaConfig.java: Configuration for Kafka.
RsaKeyProperties.java: Configuration for RSA keys, used for security.
SecurityConfig.java: Security configuration, possibly using Spring Security.
controller/: Contains REST controllers.
AuthController.java: Manages authentication endpoint.
VehicleController.java: Manages vehicle-related endpoints.
enums/: Contains enums used in the application.
CodeEnum.java: Defines various codes used.
VehicleStatus.java: Defines statuses for vehicles.
exception/: Contains custom exception classes.
VehicleApplicationException.java: Custom exception for the application.
model/: Contains model classes.
KafkaPayload.java: Defines the payload for Kafka messages.
VehicleState.java: Represents the state of a vehicle.
repository/: Contains repository interfaces.
VehicleRepository.java: Repository interface for vehicle data access.
service/: Contains service classes.
KafkaConsumerService.java: Service for consuming Kafka messages.
KafkaProducerService.java: Service for producing Kafka messages.
TokenService.java: Service for managing tokens, related to authentication.
Resources

src/main/resources/: Contains application resources.
application-h2.yml, application.yml: Configuration files for different environments.
certs/: Contains security certificates.
private.pem, public.pem: Private and public keys for security.
Tests

src/test/java/com/bridgestone/vehicles/: Test classes.
VehicleApplicationTests.java: Tests for the main application.
controller/VehicleControllerTest.java: Tests for the VehicleController.
model/KafkaPayloadTest.java: Tests for the KafkaPayload model.
service/KafkaConsumerServiceTest.java: Tests for the KafkaConsumerService.
service/KafkaProducerServiceTest.java: Tests for the KafkaProducerService.

## Getting Started

### Running the Application

1. **Clone the repository:**
   ```sh
   git clone https://github.com/GeorgeZangiev/bridgestone-vehicles.git
   cd bridgestone-vehicles
   ```

## Run the application using Docker Compose:
   ```sh 
   docker-compose up --build 
   ```

## Access the application:

The application will be available at http://localhost:8086.
Grafana will be available at http://localhost:3000.
Prometheus will be available at http://localhost:9090.
PgAdmin will be available at http://localhost:8080.

## Configuration
Application configurations are located in the src/main/resources directory.
application.yml: Main configuration file.
application-h2.yml: Configuration with H2 database instead of Postgres for running test reports.
Security certificates are located in the src/main/resources/certs directory.

## Testing
Unit tests are located in the src/test/java/com/bridgestone/vehicles directory.
To run the tests, use the following command:
```sh 
mvn clean install -P sonarPlugins -Dspring.profiles.active=h2
   ```

## Monitoring
Grafana and Prometheus configurations are located in the monitoring directory.
grafana/provisioning/datasources/datasources.yml: Configuration for Grafana data sources.
prometheus/prometheus.yml: Configuration for Prometheus.

## Contributing

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.