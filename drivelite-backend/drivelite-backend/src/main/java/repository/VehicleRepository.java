package com.drivelite.repository;


import com.drivelite.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VehicleRepository
        extends JpaRepository<Vehicle,Integer> {


    List<Vehicle> findByVehicleType(String vehicleType);


    List<Vehicle> findByAvailableTrue();


    List<Vehicle> findByPricePerDayBetween(
            double minPrice,
            double maxPrice
    );


    long countByAvailable(boolean available);


}