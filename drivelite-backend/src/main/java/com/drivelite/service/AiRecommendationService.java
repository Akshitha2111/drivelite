package com.drivelite.service;

import com.drivelite.dto.AiPredictionRequest;
import com.drivelite.dto.AiPredictionResponse;
import com.drivelite.dto.RecommendationRequest;
import com.drivelite.dto.RecommendationResponse;
import com.drivelite.dto.VehicleRecommendationDTO;
import com.drivelite.exception.AiServiceException;
import com.drivelite.model.Booking;
import com.drivelite.model.User;
import com.drivelite.model.Vehicle;
import com.drivelite.repository.BookingRepository;
import com.drivelite.repository.UserRepository;
import com.drivelite.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Core AI Recommendation engine.
 *
 * Responsibility:
 *  1. Build a candidate list of available vehicles.
 *  2. Engineer features for each candidate (type match, budget fit,
 *     popularity, user's past booking affinity).
 *  3. Send the feature vectors to the Flask AI microservice.
 *  4. Receive match probabilities and rank the candidates.
 *  5. Return the top recommendation plus a few alternatives.
 */
@Service
public class AiRecommendationService {

    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public AiRecommendationService(
            VehicleRepository vehicleRepository,
            BookingRepository bookingRepository,
            UserRepository userRepository,
            RestTemplate restTemplate) {

        this.vehicleRepository = vehicleRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    // Main entry point
    public RecommendationResponse recommendVehicle(String email, RecommendationRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Vehicle> availableVehicles = vehicleRepository.findByAvailableTrue();

        if (availableVehicles.isEmpty()) {
            throw new AiServiceException("No vehicles are currently available for recommendation");
        }

        // Popularity: total bookings per vehicle (across all users)
        Map<Integer, Long> popularityMap = bookingRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getVehicle().getId(),
                        Collectors.counting()
                ));

        // User's own booking history, used to compute type affinity
        List<Booking> userBookings = bookingRepository.findByUser(user);

        // Build feature vectors for every available vehicle
        List<AiPredictionRequest.VehicleFeature> features = new ArrayList<>();

        for (Vehicle vehicle : availableVehicles) {
            features.add(buildFeature(vehicle, request, popularityMap, userBookings));
        }

        AiPredictionResponse predictionResponse = callAiService(new AiPredictionRequest(features));

        if (predictionResponse == null
                || predictionResponse.getPredictions() == null
                || predictionResponse.getPredictions().isEmpty()) {

            throw new AiServiceException("AI service returned no predictions");
        }

        // Map vehicleId -> matchScore for quick lookup
        Map<Integer, Double> scoreMap = predictionResponse.getPredictions()
                .stream()
                .collect(Collectors.toMap(
                        AiPredictionResponse.PredictionResult::getVehicleId,
                        AiPredictionResponse.PredictionResult::getMatchScore
                ));

        // Build final ranked DTO list
        List<VehicleRecommendationDTO> ranked = availableVehicles.stream()
                .map(vehicle -> buildRecommendationDTO(
                        vehicle,
                        request,
                        scoreMap.getOrDefault(vehicle.getId(), 0.0),
                        popularityMap.getOrDefault(vehicle.getId(), 0L)
                ))
                .sorted(Comparator.comparingDouble(VehicleRecommendationDTO::getMatchScore).reversed())
                .toList();

        VehicleRecommendationDTO top = ranked.get(0);

        List<VehicleRecommendationDTO> alternatives = ranked.stream()
                .skip(1)
                .limit(2)
                .toList();

        String message = "Based on your preferences, we recommend the "
                + top.getVehicleName()
                + " (" + Math.round(top.getMatchScore() * 100) + "% match)";

        return new RecommendationResponse(top, alternatives, message);
    }

    // Builds the numeric feature vector sent to Flask for one vehicle
    private AiPredictionRequest.VehicleFeature buildFeature(
            Vehicle vehicle,
            RecommendationRequest request,
            Map<Integer, Long> popularityMap,
            List<Booking> userBookings) {

        int vehicleTypeMatch = vehicle.getVehicleType()
                .equalsIgnoreCase(request.getVehicleType()) ? 1 : 0;

        double estimatedTotalCost = vehicle.getPricePerDay() * request.getDurationInDays();

        int priceWithinBudget = estimatedTotalCost <= request.getBudget() ? 1 : 0;

        double priceToBudgetRatio = estimatedTotalCost / request.getBudget();

        long popularityScore = popularityMap.getOrDefault(vehicle.getId(), 0L);

        long userTypeAffinityScore = userBookings.stream()
                .filter(b -> b.getVehicle().getVehicleType().equalsIgnoreCase(vehicle.getVehicleType()))
                .count();

        return new AiPredictionRequest.VehicleFeature(
                vehicle.getId(),
                vehicleTypeMatch,
                priceWithinBudget,
                priceToBudgetRatio,
                request.getDurationInDays(),
                (int) popularityScore,
                (int) userTypeAffinityScore
        );
    }

    // Builds the response DTO shown to the frontend, including a readable reason
    private VehicleRecommendationDTO buildRecommendationDTO(
            Vehicle vehicle,
            RecommendationRequest request,
            double matchScore,
            long popularityScore) {

        double estimatedTotalCost = vehicle.getPricePerDay() * request.getDurationInDays();

        StringBuilder reason = new StringBuilder();

        if (vehicle.getVehicleType().equalsIgnoreCase(request.getVehicleType())) {
            reason.append("Matches your preferred vehicle type. ");
        }

        if (estimatedTotalCost <= request.getBudget()) {
            reason.append("Fits within your budget. ");
        }

        if (popularityScore >= 3) {
            reason.append("Popular among other renters. ");
        }

        if (reason.isEmpty()) {
            reason.append("Closest available match to your preferences.");
        }

        return new VehicleRecommendationDTO(
                vehicle.getId(),
                vehicle.getVehicleName(),
                vehicle.getVehicleType(),
                vehicle.getModel(),
                vehicle.getPricePerDay(),
                estimatedTotalCost,
                matchScore,
                reason.toString().trim()
        );
    }

    // Calls the Flask AI microservice and handles connectivity failures
    private AiPredictionResponse callAiService(AiPredictionRequest requestBody) {

        try {

            return restTemplate.postForObject(
                    aiServiceUrl + "/predict",
                    requestBody,
                    AiPredictionResponse.class
            );

        } catch (RestClientException ex) {

            throw new AiServiceException(
                    "Unable to reach the AI recommendation service. Please try again later.",
                    ex
            );
        }
    }
}