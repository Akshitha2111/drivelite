package com.drivelite.service;

import com.drivelite.model.Vehicle;
import com.drivelite.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Add Vehicle
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Get All Vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get Vehicle By ID
    public Vehicle getVehicleById(Integer id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));
    }

    // Update Vehicle
    public Vehicle updateVehicle(Integer id, Vehicle vehicle) {

        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setVehicleName(vehicle.getVehicleName());
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setNumberPlate(vehicle.getNumberPlate());
        existingVehicle.setPricePerDay(vehicle.getPricePerDay());
        existingVehicle.setAvailable(vehicle.isAvailable());

        return vehicleRepository.save(existingVehicle);
    }

    // Delete Vehicle
    public String deleteVehicle(Integer id) {

        vehicleRepository.deleteById(id);

        return "Vehicle deleted successfully";
    }

    // Filter by type
    public List<Vehicle> getVehiclesByType(String type) {
        return vehicleRepository.findByVehicleType(type);
    }

    // Available vehicles
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByAvailableTrue();
    }

    // Price range
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return vehicleRepository.findByPricePerDayBetween(min, max);
    }
}