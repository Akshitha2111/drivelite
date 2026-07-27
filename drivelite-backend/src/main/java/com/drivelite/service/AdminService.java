package com.drivelite.service;


import com.drivelite.dto.AdminDashboardResponse;
import com.drivelite.repository.UserRepository;
import com.drivelite.repository.VehicleRepository;

import org.springframework.stereotype.Service;



@Service
public class AdminService {


    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;



    public AdminService(
            VehicleRepository vehicleRepository,
            UserRepository userRepository
    ){

        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;

    }



    public AdminDashboardResponse getDashboard(){


        long totalVehicles =
                vehicleRepository.count();



        long availableVehicles =
                vehicleRepository.countByAvailable(true);



        long totalUsers =
                userRepository.count();



        return new AdminDashboardResponse(
                totalVehicles,
                availableVehicles,
                totalUsers
        );

    }


}