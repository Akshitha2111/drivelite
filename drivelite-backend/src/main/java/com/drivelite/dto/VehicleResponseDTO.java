package com.drivelite.dto;

public class VehicleResponseDTO {

    private Integer id;

    private String vehicleName;

    private String vehicleType;

    private String model;

    private String numberPlate;

    private double pricePerDay;

    private boolean available;


    public VehicleResponseDTO() {
    }


    public VehicleResponseDTO(Integer id,
                              String vehicleName,
                              String vehicleType,
                              String model,
                              String numberPlate,
                              double pricePerDay,
                              boolean available) {

        this.id = id;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.model = model;
        this.numberPlate = numberPlate;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }


    public Integer getId() {
        return id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getModel() {
        return model;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }
}