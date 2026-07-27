package com.drivelite.controller;

import com.drivelite.dto.AdminDashboardResponse;
import com.drivelite.dto.BookingResponseDTO;
import com.drivelite.mapper.BookingMapper;
import com.drivelite.service.AdminService;
import com.drivelite.service.BookingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;
    private final BookingService bookingService;

    public AdminController(
            AdminService adminService,
            BookingService bookingService
    ) {
        this.adminService = adminService;
        this.bookingService = bookingService;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {

        return adminService.getDashboard();

    }

    @GetMapping("/bookings")
    public List<BookingResponseDTO> getAllBookings() {

        return bookingService.getAllBookings()
                .stream()
                .map(BookingMapper::toDTO)
                .toList();

    }

}