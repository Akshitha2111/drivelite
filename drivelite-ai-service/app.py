"""
app.py

Flask AI microservice for DriveLite's Vehicle Recommendation feature.

Loads the trained RandomForestClassifier ONCE at startup, then
exposes a /predict endpoint that Spring Boot's AiRecommendationService
calls with a batch of candidate vehicle feature vectors.

Endpoints:
    GET  /health   - simple health check
    POST /predict  - accepts { "candidates": [ {...features...}, ... ] }
                     returns { "predictions": [ {"vehicleId": .., "matchScore": ..}, ... ] }
"""

import os

import joblib
import pandas as pd
from flask import Flask, jsonify, request
from flask_cors import CORS

MODEL_PATH = "vehicle_recommendation_model.pkl"

FEATURE_COLUMNS = [
    "vehicleTypeMatch",
    "priceWithinBudget",
    "priceToBudgetRatio",
    "durationInDays",
    "popularityScore",
    "userTypeAffinityScore",
]

app = Flask(__name__)
CORS(app)

# ---------------- Load model once at startup ----------------
if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError(
        f"Model file '{MODEL_PATH}' not found. "
        f"Run 'python train_model.py' before starting app.py."
    )

model = joblib.load(MODEL_PATH)
print(f"AI model loaded successfully from {MODEL_PATH}")


@app.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "UP", "service": "drivelite-ai-service"}), 200


@app.route("/predict", methods=["POST"])
def predict():

    try:
        body = request.get_json(force=True)
    except Exception:
        return jsonify({"error": "Invalid JSON body"}), 400

    if not body or "candidates" not in body or not body["candidates"]:
        return jsonify({"error": "Request must contain a non-empty 'candidates' list"}), 400

    candidates = body["candidates"]

    try:
        # Build a DataFrame in the exact column order the model was trained on
        rows = []
        vehicle_ids = []

        for candidate in candidates:

            vehicle_ids.append(candidate.get("vehicleId"))

            rows.append({
                "vehicleTypeMatch": candidate.get("vehicleTypeMatch", 0),
                "priceWithinBudget": candidate.get("priceWithinBudget", 0),
                "priceToBudgetRatio": candidate.get("priceToBudgetRatio", 1.0),
                "durationInDays": candidate.get("durationInDays", 1),
                "popularityScore": candidate.get("popularityScore", 0),
                "userTypeAffinityScore": candidate.get("userTypeAffinityScore", 0),
            })

        df = pd.DataFrame(rows, columns=FEATURE_COLUMNS)

        # predict_proba returns [P(class=0), P(class=1)] per row.
        # We want P(class=1) -> probability of being a "good match"
        probabilities = model.predict_proba(df)[:, 1]

        predictions = [
            {"vehicleId": vehicle_ids[i], "matchScore": round(float(probabilities[i]), 4)}
            for i in range(len(vehicle_ids))
        ]

        return jsonify({"predictions": predictions}), 200

    except Exception as ex:
        return jsonify({"error": f"Prediction failed: {str(ex)}"}), 500


if __name__ == "__main__":
    # Runs on port 5000 to match ai.service.url in application.properties
    app.run(host="0.0.0.0", port=5000, debug=False)