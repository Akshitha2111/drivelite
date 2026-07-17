import api from "./api";

// ================= AI RECOMMENDATION APIs =================

// Get AI-powered vehicle recommendation based on user preferences
export const getRecommendation = (preferences) => {
    return api.post("/ai/recommend", preferences);
};