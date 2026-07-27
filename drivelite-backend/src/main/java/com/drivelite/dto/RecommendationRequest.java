package com.drivelite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

/**
 * Incoming request from the frontend when a user asks
 * the AI engine to recommend the best vehicle for them.
 *
 * vehicleType   - preferred vehicle category (Bike / Car / Scooter / SUV)
 * budget        - maximum amount the user is willing to spend (total, not per day)
 * durationInDays - number of days the user wants to rent for
 */
public class RecommendationRequest {

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotNull(message = "Budget is required")
    @Min(value = 1, message = "Budget must be greater than zero")
    private Double budget;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationInDays;

    public RecommendationRequest() {
    }

    public RecommendationRequest(String vehicleType, Double budget, Integer durationInDays) {
        this.vehicleType = vehicleType;
        this.budget = budget;
        this.durationInDays = durationInDays;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }
}