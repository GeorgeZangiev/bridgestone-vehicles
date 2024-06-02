package com.bridgestone.vehicles;

import com.bridgestone.vehicles.config.RsaKeyProperties;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Generated
@EnableConfigurationProperties(RsaKeyProperties.class)
public class VehicleApplication {
	public static void main(String[] args) {
		SpringApplication.run(VehicleApplication.class, args);
	}
}
