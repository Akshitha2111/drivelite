package com.drivelite.dto;

import java.util.List;

/**
 * Internal DTO sent FROM Spring Boot TO the Flask AI Service.
 * Wraps a batch of candidate vehicles (already filtered for
 * availability) so the model can score all of them in one call.
 */
public class AiPredictionRequest {

    private List<VehicleFeature> candidates;

    public AiPredictionRequest() {
    }

    public AiPredictionRequest(List<VehicleFeature> candidates) {
        this.candidates = candidates;
    }

    public List<VehicleFeature> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<VehicleFeature> candidates) {
        this.candidates = candidates;
    }

    /**
     * Feature vector for a single candidate vehicle, matching the
     * exact columns the Scikit-learn model was trained on:
     *
     * vehicleId              - used only to map the result back, not a training feature
     * vehicleTypeMatch        - 1 if vehicle type equals requested type, else 0
     * priceWithinBudget       - 1 if (pricePerDay * durationInDays) <= budget, else 0
     * priceToBudgetRatio      - estimatedTotalCost / budget (closer to 1 = better fit)
     * durationInDays          - requested rental duration
     * popularityScore         - total number of past bookings for this vehicle
     * userTypeAffinityScore   - how many times this user has booked this vehicle type before
     */
    public static class VehicleFeature {

        private Integer vehicleId;
        private int vehicleTypeMatch;
        private int priceWithinBudget;
        private double priceToBudgetRatio;
        private int durationInDays;
        private int popularityScore;
        private int userTypeAffinityScore;

        public VehicleFeature() {
        }

        public VehicleFeature(Integer vehicleId,
                              int vehicleTypeMatch,
                              int priceWithinBudget,
                              double priceToBudgetRatio,
                              int durationInDays,
                              int popularityScore,
                              int userTypeAffinityScore) {

            this.vehicleId = vehicleId;
            this.vehicleTypeMatch = vehicleTypeMatch;
            this.priceWithinBudget = priceWithinBudget;
            this.priceToBudgetRatio = priceToBudgetRatio;
            this.durationInDays = durationInDays;
            this.popularityScore = popularityScore;
            this.userTypeAffinityScore = userTypeAffinityScore;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public int getVehicleTypeMatch() {
            return vehicleTypeMatch;
        }

        public void setVehicleTypeMatch(int vehicleTypeMatch) {
            this.vehicleTypeMatch = vehicleTypeMatch;
        }

        public int getPriceWithinBudget() {
            return priceWithinBudget;
        }

        public void setPriceWithinBudget(int priceWithinBudget) {
            this.priceWithinBudget = priceWithinBudget;
        }

        public double getPriceToBudgetRatio() {
            return priceToBudgetRatio;
        }

        public void setPriceToBudgetRatio(double priceToBudgetRatio) {
            this.priceToBudgetRatio = priceToBudgetRatio;
        }

        public int getDurationInDays() {
            return durationInDays;
        }

        public void setDurationInDays(int durationInDays) {
            this.durationInDays = durationInDays;
        }

        public int getPopularityScore() {
            return popularityScore;
        }

        public void setPopularityScore(int popularityScore) {
            this.popularityScore = popularityScore;
        }

        public int getUserTypeAffinityScore() {
            return userTypeAffinityScore;
        }

        public void setUserTypeAffinityScore(int userTypeAffinityScore) {
            this.userTypeAffinityScore = userTypeAffinityScore;
        }
    }
}