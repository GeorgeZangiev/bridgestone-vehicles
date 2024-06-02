
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


- Dockerfile: Used to containerize the application.
- docker-compose.yaml: Defines the services for the application, possibly including the application itself, a database, and other services like Kafka.
- lombok.config: Configuration for Lombok, a Java library that helps to reduce
  boilerplate code.
- pom.xml: Maven configuration file, defining dependencies, plugins, and other project-specific configurations.



## Monitoring


- monitoring/grafana/provisioning/datasources/datasources.yml: Configuration for Grafana data sources.
- monitoring/prometheus/prometheus.yml: Configuration for Prometheus, a monitoring and alerting toolkit.



## Source Code

0. VehicleApplication.java: Entry point for the Spring Boot application.
1. config/: Contains configuration classes.  
   1.1. KafkaConfig.java: Configuration for Kafka.  
   1.2. RsaKeyProperties.java: Configuration for RSA keys, used for security.  
   1.3. SecurityConfig.java: Security configuration, possibly using Spring Security.
2. controller/: Contains REST controllers.  
   2.1. AuthController.java: Manages authentication endpoint.  
   2.2. VehicleController.java: Manages vehicle-related endpoints.
3. enums/: Contains enums used in the application.  
   3.1. CodeEnum.java: Defines various codes used.  
   3.2. VehicleStatus.java: Defines statuses for vehicles.
4. exception/: Contains custom exception classes.  
   4.1. VehicleApplicationException.java: Custom exception for the application.
5. model/: Contains model classes.  
   5.1. KafkaPayload.java: Defines the payload for Kafka messages.  
   5.2. VehicleState.java: Represents the state of a vehicle.
6. repository/: Contains repository interfaces.  
   6.1. VehicleRepository.java: Repository interface for vehicle data access.
7. service/: Contains service classes.  
   7.1. KafkaConsumerService.java: Service for consuming Kafka messages.  
   7.2. KafkaProducerService.java: Service for producing Kafka messages.  
   7.3. TokenService.java: Service for managing tokens, related to authentication.


## Resources


1. src/main/resources/: Contains application resources.
   1.1. application-h2.yml, application.yml: Configuration files for different environments.
2. certs/: Contains security certificates.  
   2.1. private.pem, public.pem: Private and public keys for security.

## Tests

1. src/test/java/com/bridgestone/vehicles/: Test classes.  
   1.1. VehicleApplicationTests.java: Tests for the main application.  
   1.2. controller/VehicleControllerTest.java: Tests for the VehicleController.  
   1.3. model/KafkaPayloadTest.java: Tests for the KafkaPayload model.  
   1.4. service/KafkaConsumerServiceTest.java: Tests for the KafkaConsumerService.  
   1.5. service/KafkaProducerServiceTest.java: Tests for the KafkaProducerService.

## Getting Started

### Running the Application

- **Clone the repository:**
 ```sh  
  git clone https://github.com/GeorgeZangiev/bridgestone-vehicles.git
  cd bridgestone-vehicles
  ## Run the application using Docker Compose:    
  docker-compose build
  docker-compose up
 ```  
## Access the application:

- The application will be available at http://localhost:8086.
-  Grafana will be available at http://localhost:3000.
-   Prometheus will be available at http://localhost:9090.
-   PgAdmin will be available at http://localhost:8080.

## Configuration
Application configurations are located in the src/main/resources directory.
- application.yml: Main configuration file.
- application-h2.yml: Configuration with H2 database instead of Postgres for running test reports.
- Security certificates are located in the src/main/resources/certs directory.

## Testing
Unit tests are located in the src/test/java/com/bridgestone/vehicles directory.  
To run the tests, use the following command:
```sh 
mvn clean install -P sonarPlugins -Dspring.profiles.active=h2 
```  
## Monitoring
Grafana and Prometheus configurations are located in the monitoring directory.
- grafana/provisioning/datasources/datasources.yml: Configuration for Grafana data sources.
-   prometheus/prometheus.yml: Configuration for Prometheus.

## Contributing

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Create a new Pull Request.