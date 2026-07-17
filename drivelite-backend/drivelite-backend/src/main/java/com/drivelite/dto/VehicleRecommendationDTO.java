package com.drivelite.dto;

/**
 * Represents a single vehicle scored by the AI recommendation
 * engine, along with the match probability returned by the
 * Scikit-learn model and a short human readable reason.
 */
public class VehicleRecommendationDTO {

    private Integer vehicleId;

    private String vehicleName;

    private String vehicleType;

    private String model;

    private double pricePerDay;

    private double estimatedTotalCost;

    private double matchScore; // probability (0.0 - 1.0) returned by the AI model

    private String reason;

    public VehicleRecommendationDTO() {
    }

    public VehicleRecommendationDTO(Integer vehicleId,
                                    String vehicleName,
                                    String vehicleType,
                                    String model,
                                    double pricePerDay,
                                    double estimatedTotalCost,
                                    double matchScore,
                                    String reason) {

        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.estimatedTotalCost = estimatedTotalCost;
        this.matchScore = matchScore;
        this.reason = reason;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(double estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}