package com.drivelite.service;

import com.drivelite.dto.BookingRequest;
import com.drivelite.model.Booking;
import com.drivelite.model.User;
import com.drivelite.model.Vehicle;
import com.drivelite.repository.BookingRepository;
import com.drivelite.repository.UserRepository;
import com.drivelite.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import com.drivelite.exception.VehicleNotAvailableException;



import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            VehicleRepository vehicleRepository) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // Create Booking
    public Booking createBooking(String email, BookingRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(
                    "Vehicle is not available"
            );
        }

        boolean overlap = bookingRepository.existsOverlappingBooking(
                vehicle.getId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (overlap) {
            throw new RuntimeException("Vehicle already booked for these dates");
        }

        long days = ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        );

        if (days <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        double totalAmount = days * vehicle.getPricePerDay();

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("BOOKED");

        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        return bookingRepository.save(booking);
    }

    // Get All Bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get Booking By Id
    public Booking getBookingById(Integer id) {

        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));
    }

    // Cancel Booking
    public Booking cancelBooking(Integer id) {

        Booking booking = getBookingById(id);

        Vehicle vehicle = booking.getVehicle();

        vehicle.setAvailable(true);

        vehicleRepository.save(vehicle);

        booking.setStatus("CANCELLED");

        return bookingRepository.save(booking);
    }

    // Complete Booking
    public Booking completeBooking(Integer id) {

        Booking booking = getBookingById(id);

        Vehicle vehicle = booking.getVehicle();

        vehicle.setAvailable(true);

        vehicleRepository.save(vehicle);

        booking.setStatus("COMPLETED");

        return bookingRepository.save(booking);
    }

    // Delete Booking
    public String deleteBooking(Integer id) {

        bookingRepository.deleteById(id);

        return "Booking deleted successfully";
    }
    public List<Booking> getBookingsByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return bookingRepository.findByUser(user);
    }
}