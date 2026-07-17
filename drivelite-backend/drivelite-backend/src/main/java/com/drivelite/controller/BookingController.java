package com.drivelite.controller;

import com.drivelite.dto.BookingRequest;
import com.drivelite.dto.BookingResponseDTO;
import com.drivelite.mapper.BookingMapper;
import com.drivelite.model.Booking;
import com.drivelite.service.BookingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import jakarta.validation.Valid;
import com.drivelite.dto.MyBookingResponseDTO;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create Booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO createBooking(
            Authentication authentication,
            @Valid @RequestBody BookingRequest request) {

        Booking savedBooking = bookingService.createBooking(
                authentication.getName(),
                request
        );

        return BookingMapper.toDTO(savedBooking);
    }

    // Get All Bookings
    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {

        return bookingService.getAllBookings()
                .stream()
                .map(BookingMapper::toDTO)
                .toList();
    }

    // Get Booking By ID
    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(
            @PathVariable Integer id) {

        Booking booking = bookingService.getBookingById(id);

        return BookingMapper.toDTO(booking);
    }

    // Cancel Booking
    @PutMapping("/{id}/cancel")
    public BookingResponseDTO cancelBooking(
            @PathVariable Integer id) {

        Booking booking = bookingService.cancelBooking(id);

        return BookingMapper.toDTO(booking);
    }

    // Complete Booking
    @PutMapping("/{id}/complete")
    public BookingResponseDTO completeBooking(
            @PathVariable Integer id) {

        Booking booking = bookingService.completeBooking(id);

        return BookingMapper.toDTO(booking);
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(
            @PathVariable Integer id) {

        bookingService.deleteBooking(id);
    }
    @GetMapping("/my")
    public List<MyBookingResponseDTO> getMyBookings(
            Authentication authentication) {

        return bookingService
                .getBookingsByUser(authentication.getName())
                .stream()
                .map(BookingMapper::toMyBookingDTO)
                .toList();
    }
}
