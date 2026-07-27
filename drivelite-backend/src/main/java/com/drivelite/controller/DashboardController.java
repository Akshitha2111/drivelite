package com.drivelite.controller;

import com.drivelite.dto.DashboardResponse;
import com.drivelite.repository.BookingRepository;
import com.drivelite.repository.UserRepository;
import com.drivelite.repository.VehicleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;

    public DashboardController(
            UserRepository userRepository,
            VehicleRepository vehicleRepository,
            BookingRepository bookingRepository) {

        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalUsers(userRepository.count());

        response.setTotalVehicles(vehicleRepository.count());

        response.setAvailableVehicles(
                vehicleRepository.findByAvailableTrue().size()
        );

        response.setBookedVehicles(
                vehicleRepository.count()
                        - vehicleRepository.findByAvailableTrue().size()
        );

        response.setTotalBookings(
                bookingRepository.count()
        );

        return response;
    }
}