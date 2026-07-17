import { useState } from "react";

function RecommendationForm({ onSubmit, loading }) {

    const [preferences, setPreferences] = useState({
        vehicleType: "Car",
        budget: "",
        durationInDays: ""
    });

    const handleChange = (e) => {

        setPreferences({
            ...preferences,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        if (!preferences.budget || !preferences.durationInDays) {
            alert("Please fill in your budget and duration.");
            return;
        }

        onSubmit({
            vehicleType: preferences.vehicleType,
            budget: Number(preferences.budget),
            durationInDays: Number(preferences.durationInDays)
        });

    };

    return (

        <div className="card shadow p-4 mb-4">

            <h4 className="mb-3">Tell us what you need</h4>

            <form onSubmit={handleSubmit}>

                <div className="mb-3">

                    <label className="form-label">
                        Preferred Vehicle Type
                    </label>

                    <select
                        className="form-select"
                        name="vehicleType"
                        value={preferences.vehicleType}
                        onChange={handleChange}
                    >
                        <option>Car</option>
                        <option>Bike</option>
                        <option>Scooter</option>
                        <option>SUV</option>
                    </select>

                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Rental Budget (₹, total)
                    </label>

                    <input
                        type="number"
                        className="form-control"
                        name="budget"
                        placeholder="e.g. 5000"
                        value={preferences.budget}
                        onChange={handleChange}
                        min="1"
                    />

                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Booking Duration (days)
                    </label>

                    <input
                        type="number"
                        className="form-control"
                        name="durationInDays"
                        placeholder="e.g. 3"
                        value={preferences.durationInDays}
                        onChange={handleChange}
                        min="1"
                    />

                </div>

                <button
                    type="submit"
                    className="btn btn-primary w-100"
                    disabled={loading}
                >
                    {loading ? "Finding your best match..." : "Get AI Recommendation"}
                </button>

            </form>

        </div>

    );
}

export default RecommendationForm;