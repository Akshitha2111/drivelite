package com.drivelite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MyBookingResponseDTO {

    private Integer id;

    private Integer vehicleId;

    private String vehicleName;

    private String vehicleType;

    private String model;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double totalAmount;

    private String status;
}