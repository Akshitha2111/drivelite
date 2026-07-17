import { useState } from "react";
import { getRecommendation } from "../services/aiService";
import RecommendationForm from "../components/RecommendationForm";
import RecommendationCard from "../components/RecommendationCard";
import Loader from "../components/Loader";

function Recommendation() {

    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleGetRecommendation = async (preferences) => {

        setLoading(true);
        setError("");
        setResult(null);

        try {

            const response = await getRecommendation(preferences);
            setResult(response.data);

        } catch (err) {

            console.error(err);

            const message =
                err.response?.data?.error ||
                "Something went wrong while getting your recommendation. Please try again.";

            setError(message);

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="container mt-4 mb-5">

            <h2 className="mb-4 text-center">
                AI Vehicle Recommendation
            </h2>

            <div className="row justify-content-center">

                <div className="col-md-6">

                    <RecommendationForm
                        onSubmit={handleGetRecommendation}
                        loading={loading}
                    />

                </div>

            </div>

            {loading && <Loader text="Analyzing available vehicles..." />}

            {error && (

                <div className="alert alert-danger text-center">
                    {error}
                </div>

            )}

            {result && !loading && (

                <>
                    <div className="alert alert-info text-center">
                        {result.message}
                    </div>

                    <div className="row">

                        <div className="col-md-4 mb-4">
                            <RecommendationCard
                                vehicle={result.topRecommendation}
                                highlight={true}
                            />
                        </div>

                        {result.alternatives?.map((vehicle) => (

                            <div className="col-md-4 mb-4" key={vehicle.vehicleId}>
                                <RecommendationCard vehicle={vehicle} />
                            </div>

                        ))}

                    </div>
                </>

            )}

        </div>

    );
}

export default Recommendation;