package com.bridgestone.vehicles.repository;

import com.bridgestone.vehicles.model.VehicleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleState, Long> {

    List<VehicleState> findAllByVehicleId(Long vehicleId);
}
