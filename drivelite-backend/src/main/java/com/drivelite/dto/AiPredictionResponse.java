package com.drivelite.dto;

import java.util.List;

/**
 * Internal DTO received FROM the Flask AI Service back INTO
 * Spring Boot. Contains the model's predicted match score for
 * every candidate vehicle that was sent in AiPredictionRequest.
 */
public class AiPredictionResponse {

    private List<PredictionResult> predictions;

    public AiPredictionResponse() {
    }

    public AiPredictionResponse(List<PredictionResult> predictions) {
        this.predictions = predictions;
    }

    public List<PredictionResult> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionResult> predictions) {
        this.predictions = predictions;
    }

    /**
     * Prediction result for a single vehicle.
     *
     * vehicleId    - maps back to the original candidate
     * matchScore   - probability (0.0 - 1.0) that this vehicle is a
     *                "good match" for the user, as predicted by the
     *                RandomForestClassifier
     */
    public static class PredictionResult {

        private Integer vehicleId;
        private double matchScore;

        public PredictionResult() {
        }

        public PredictionResult(Integer vehicleId, double matchScore) {
            this.vehicleId = vehicleId;
            this.matchScore = matchScore;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public double getMatchScore() {
            return matchScore;
        }

        public void setMatchScore(double matchScore) {
            this.matchScore = matchScore;
        }
    }
}