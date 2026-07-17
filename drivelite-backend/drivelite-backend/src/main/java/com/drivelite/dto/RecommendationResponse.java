package com.drivelite.dto;

import java.util.List;

/**
 * Final response sent back to the frontend after the AI
 * recommendation engine has ranked all eligible vehicles.
 *
 * topRecommendation - the single best vehicle match
 * alternatives       - next best matches (e.g. 2nd and 3rd choice)
 * message            - human readable summary for the UI
 */
public class RecommendationResponse {

    private VehicleRecommendationDTO topRecommendation;

    private List<VehicleRecommendationDTO> alternatives;

    private String message;

    public RecommendationResponse() {
    }

    public RecommendationResponse(VehicleRecommendationDTO topRecommendation,
                                  List<VehicleRecommendationDTO> alternatives,
                                  String message) {

        this.topRecommendation = topRecommendation;
        this.alternatives = alternatives;
        this.message = message;
    }

    public VehicleRecommendationDTO getTopRecommendation() {
        return topRecommendation;
    }

    public void setTopRecommendation(VehicleRecommendationDTO topRecommendation) {
        this.topRecommendation = topRecommendation;
    }

    public List<VehicleRecommendationDTO> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<VehicleRecommendationDTO> alternatives) {
        this.alternatives = alternatives;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}