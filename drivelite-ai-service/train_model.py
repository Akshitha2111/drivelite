"""
train_model.py

Trains the DriveLite AI Vehicle Recommendation model.

Pipeline:
    1. Load the synthetic dataset (generate it if missing)
    2. Data cleaning (drop nulls / duplicates)
    3. Feature / label split
    4. Train/test split
    5. Train a RandomForestClassifier
    6. Evaluate accuracy
    7. Save the trained model with Joblib for Flask to load

Run this file once before starting app.py. Re-run any time you
want to retrain (e.g. after changing dataset_generator.py).
"""

import os

import joblib
import pandas as pd

from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report
from sklearn.model_selection import train_test_split

from dataset_generator import save_dataset

DATASET_PATH = "vehicle_recommendation_dataset.csv"
MODEL_PATH = "vehicle_recommendation_model.pkl"

FEATURE_COLUMNS = [
    "vehicleTypeMatch",
    "priceWithinBudget",
    "priceToBudgetRatio",
    "durationInDays",
    "popularityScore",
    "userTypeAffinityScore",
]

LABEL_COLUMN = "isGoodMatch"


def load_dataset() -> pd.DataFrame:

    if not os.path.exists(DATASET_PATH):
        print("Dataset not found. Generating a new one...")
        save_dataset(DATASET_PATH)

    return pd.read_csv(DATASET_PATH)


def clean_dataset(df: pd.DataFrame) -> pd.DataFrame:

    # Data Cleaning
    df = df.dropna()
    df = df.drop_duplicates()

    return df


def train_and_evaluate() -> None:

    # 1. Load
    df = load_dataset()
    print(f"Loaded dataset with {len(df)} rows")

    # 2. Clean
    df = clean_dataset(df)
    print(f"After cleaning: {len(df)} rows")

    # 3. Feature Engineering / Split into X and y
    X = df[FEATURE_COLUMNS]
    y = df[LABEL_COLUMN]

    # 4. Train/Test Split
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42, stratify=y
    )

    # 5. Model Training
    model = RandomForestClassifier(
        n_estimators=200,
        max_depth=8,
        random_state=42,
        class_weight="balanced",
    )

    model.fit(X_train, y_train)

    # 6. Accuracy Evaluation
    y_pred = model.predict(X_test)
    accuracy = accuracy_score(y_test, y_pred)

    print(f"\nModel Accuracy: {accuracy * 100:.2f}%")
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred))

    # Feature importance (useful to explain the model in an interview)
    importances = pd.Series(model.feature_importances_, index=FEATURE_COLUMNS)
    importances = importances.sort_values(ascending=False)

    print("Feature Importances:")
    print(importances)

    # 7. Save Model using Joblib
    joblib.dump(model, MODEL_PATH)
    print(f"\nModel saved to: {MODEL_PATH}")


if __name__ == "__main__":
    train_and_evaluate()