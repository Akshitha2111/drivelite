"""
dataset_generator.py

Generates a realistic SYNTHETIC dataset used to train the
DriveLite AI Vehicle Recommendation model.

Each row represents one "candidate vehicle shown to a user" event,
with the exact same feature columns that Spring Boot's
AiRecommendationService sends to Flask at prediction time:

    vehicleTypeMatch        - 1 if vehicle type matches user's preferred type
    priceWithinBudget       - 1 if estimated total cost <= user's budget
    priceToBudgetRatio      - estimatedTotalCost / budget
    durationInDays          - requested rental duration
    popularityScore         - number of past bookings for this vehicle
    userTypeAffinityScore   - number of times this user booked this vehicle type before

Label:
    isGoodMatch (1 = good recommendation, 0 = poor recommendation)

The labeling logic below is rule-based with added random noise,
which keeps the dataset realistic while still being fully
explainable in an interview ("here is exactly why a row is
labeled good or bad match").
"""

import numpy as np
import pandas as pd

# Reproducible results
np.random.seed(42)

NUM_SAMPLES = 6000


def generate_dataset(num_samples: int = NUM_SAMPLES) -> pd.DataFrame:

    vehicle_type_match = np.random.choice([0, 1], size=num_samples, p=[0.4, 0.6])

    price_within_budget = np.random.choice([0, 1], size=num_samples, p=[0.35, 0.65])

    # Ratio tends to be near 1.0 when within budget, higher when over budget
    price_to_budget_ratio = np.where(
        price_within_budget == 1,
        np.round(np.random.uniform(0.3, 1.0, num_samples), 2),
        np.round(np.random.uniform(1.01, 2.5, num_samples), 2)
    )

    duration_in_days = np.random.randint(1, 15, size=num_samples)

    popularity_score = np.random.poisson(lam=4, size=num_samples)

    user_type_affinity_score = np.random.poisson(lam=1.5, size=num_samples)

    df = pd.DataFrame({
        "vehicleTypeMatch": vehicle_type_match,
        "priceWithinBudget": price_within_budget,
        "priceToBudgetRatio": price_to_budget_ratio,
        "durationInDays": duration_in_days,
        "popularityScore": popularity_score,
        "userTypeAffinityScore": user_type_affinity_score,
    })

    # ---------------- Rule-based label generation ----------------
    # A vehicle is a "good match" when it scores well across the
    # weighted signals below. Small random noise is added so the
    # model has to genuinely learn patterns instead of memorizing
    # a perfect rule (keeps it a real ML problem, not a lookup table).

    score = (
        df["vehicleTypeMatch"] * 3.0
        + df["priceWithinBudget"] * 3.0
        + (1 / df["priceToBudgetRatio"].clip(lower=0.3)) * 1.0
        + (df["popularityScore"] / df["popularityScore"].max()) * 1.5
        + (df["userTypeAffinityScore"] / (df["userTypeAffinityScore"].max() + 1)) * 1.5
    )

    noise = np.random.normal(loc=0, scale=0.8, size=num_samples)
    final_score = score + noise

    threshold = np.percentile(final_score, 45)  # ~55% positive class

    df["isGoodMatch"] = (final_score >= threshold).astype(int)

    return df


def save_dataset(path: str = "vehicle_recommendation_dataset.csv") -> None:

    df = generate_dataset()
    df.to_csv(path, index=False)

    print(f"Dataset generated: {path}")
    print(f"Total rows: {len(df)}")
    print(f"Good match distribution:\n{df['isGoodMatch'].value_counts()}")


if __name__ == "__main__":
    save_dataset()