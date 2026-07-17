package com.drivelite.controller;

import com.drivelite.model.Vehicle;
import com.drivelite.service.VehicleService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Add Vehicle
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle createVehicle(@Valid @RequestBody Vehicle vehicle) {

        return vehicleService.createVehicle(vehicle);
    }

    // Get All Vehicles
    @GetMapping
    public List<Vehicle> getVehicles() {

        return vehicleService.getAllVehicles();
    }

    // Get Vehicle By ID
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Integer id) {

        return vehicleService.getVehicleById(id);
    }

    // Update Vehicle
    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable Integer id,
            @Valid @RequestBody Vehicle vehicle) {

        return vehicleService.updateVehicle(id, vehicle);
    }

    // Delete Vehicle
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Integer id) {

        vehicleService.deleteVehicle(id);
    }

    // Get Vehicles By Type
    @GetMapping("/type/{type}")
    public List<Vehicle> getVehiclesByType(@PathVariable String type) {

        return vehicleService.getVehiclesByType(type);
    }

    // Get Available Vehicles
    @GetMapping("/available")
    public List<Vehicle> getAvailableVehicles() {

        return vehicleService.getAvailableVehicles();
    }

    // Get Vehicles By Price Range
    @GetMapping("/price")
    public List<Vehicle> getVehiclesByPrice(
            @RequestParam double min,
            @RequestParam double max) {

        return vehicleService.getVehiclesByPrice(min, max);
    }
}