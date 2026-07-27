package com.drivelite.mapper;

import com.drivelite.dto.*;
import com.drivelite.model.Booking;

public class BookingMapper {


    public static BookingResponseDTO toDTO(Booking booking){

        UserResponseDTO user =
                new UserResponseDTO(
                        booking.getUser().getId(),
                        booking.getUser().getName(),
                        booking.getUser().getEmail(),
                        booking.getUser().getPhone(),
                        booking.getUser().getRole()
                );


        VehicleResponseDTO vehicle =
                new VehicleResponseDTO(
                        booking.getVehicle().getId(),
                        booking.getVehicle().getVehicleName(),
                        booking.getVehicle().getVehicleType(),
                        booking.getVehicle().getModel(),
                        booking.getVehicle().getNumberPlate(),
                        booking.getVehicle().getPricePerDay(),
                        booking.getVehicle().isAvailable()
                );


        return new BookingResponseDTO(
                booking.getId(),
                user,
                vehicle,
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalAmount(),
                booking.getStatus()
        );
    }
    public static MyBookingResponseDTO toMyBookingDTO(
            Booking booking
    ) {

        return new MyBookingResponseDTO(

                booking.getId(),

                booking.getVehicle().getId(),

                booking.getVehicle().getVehicleName(),

                booking.getVehicle().getVehicleType(),

                booking.getVehicle().getModel(),

                booking.getStartDate(),

                booking.getEndDate(),

                booking.getTotalAmount(),

                booking.getStatus()

        );

    }
}