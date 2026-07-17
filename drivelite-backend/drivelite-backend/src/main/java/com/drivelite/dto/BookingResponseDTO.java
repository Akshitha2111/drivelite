package com.drivelite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingResponseDTO {

    private Integer id;

    private UserResponseDTO user;

    private VehicleResponseDTO vehicle;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double totalAmount;

    private String status;
}