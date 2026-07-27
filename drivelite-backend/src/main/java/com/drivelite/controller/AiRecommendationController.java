package com.drivelite.controller;

import com.drivelite.dto.RecommendationRequest;
import com.drivelite.dto.RecommendationResponse;
import com.drivelite.service.AiRecommendationService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes the AI Vehicle Recommendation feature to the frontend.
 *
 * Flow:
 * React -> POST /ai/recommend -> AiRecommendationService -> Flask -> Scikit-learn model
 */
@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AiRecommendationController {

    private final AiRecommendationService aiRecommendationService;

    public AiRecommendationController(AiRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    // Get AI-powered vehicle recommendation for the logged-in user
    @PostMapping("/recommend")
    public RecommendationResponse recommendVehicle(
            Authentication authentication,
            @Valid @RequestBody RecommendationRequest request) {

        return aiRecommendationService.recommendVehicle(
                authentication.getName(),
                request
        );
    }
}