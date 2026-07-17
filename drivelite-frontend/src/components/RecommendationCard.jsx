function RecommendationCard({ vehicle, highlight = false }) {

    const icon =
        vehicle.vehicleType === "Bike" ? "🏍️" :
            vehicle.vehicleType === "Car" ? "🚗" :
                vehicle.vehicleType === "Scooter" ? "🛵" :
                    vehicle.vehicleType === "SUV" ? "🚙" : "🚘";

    return (

        <div className={`card shadow h-100 ${highlight ? "border-primary border-2" : ""}`}>

            <div className="card-body">

                {highlight && (
                    <span className="badge bg-primary mb-2">
                        Best Match
                    </span>
                )}

                <h2 className="text-center mb-3">{icon}</h2>

                <h5>{vehicle.vehicleName}</h5>

                <p>
                    <strong>Type:</strong> {vehicle.vehicleType}
                </p>

                <p>
                    <strong>Model:</strong> {vehicle.model}
                </p>

                <p>
                    <strong>Price:</strong> ₹{vehicle.pricePerDay}/day
                </p>

                <p>
                    <strong>Estimated Total:</strong> ₹{vehicle.estimatedTotalCost}
                </p>

                <div className="progress mb-2" style={{ height: "8px" }}>

                    <div
                        className="progress-bar bg-success"
                        role="progressbar"
                        style={{ width: `${Math.round(vehicle.matchScore * 100)}%` }}
                    />

                </div>

                <p className="text-muted small mb-2">
                    {Math.round(vehicle.matchScore * 100)}% Match
                </p>

                <p className="small fst-italic">
                    {vehicle.reason}
                </p>

            </div>

        </div>

    );
}

export default RecommendationCard;