package com.drivelite.controller;


import com.drivelite.model.Vehicle;
import com.drivelite.repository.VehicleRepository;

import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/admin/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminVehicleController {



    private final VehicleRepository vehicleRepository;



    public AdminVehicleController(
            VehicleRepository vehicleRepository
    ){

        this.vehicleRepository = vehicleRepository;

    }



    // View all vehicles
    @GetMapping
    public List<Vehicle> getVehicles(){

        return vehicleRepository.findAll();

    }




    // Add vehicle
    @PostMapping
    public Vehicle addVehicle(
            @RequestBody Vehicle vehicle
    ){

        return vehicleRepository.save(vehicle);

    }





    // Update vehicle
    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable Integer id,
            @RequestBody Vehicle vehicle
    ){


        vehicle.setId(id);


        return vehicleRepository.save(vehicle);

    }





    // Delete vehicle
    @DeleteMapping("/{id}")
    public String deleteVehicle(
            @PathVariable Integer id
    ){


        vehicleRepository.deleteById(id);


        return "Vehicle deleted successfully";

    }



}