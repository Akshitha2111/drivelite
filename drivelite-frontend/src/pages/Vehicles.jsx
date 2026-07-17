import { useEffect, useState } from "react";
import { getVehicles } from "../services/vehicleService";
import { createBooking } from "../services/bookingService";

function Vehicles() {

    const [vehicles, setVehicles] = useState([]);

    const [booking, setBooking] = useState({
        vehicleId: "",
        startDate: "",
        endDate: ""
    });

    const [selectedVehicle, setSelectedVehicle] = useState(null);
    const [totalAmount, setTotalAmount] = useState(0);

    const [search, setSearch] = useState("");
    const [typeFilter, setTypeFilter] = useState("All");
    const [sortOrder, setSortOrder] = useState("");

    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = async () => {

        try {

            const response = await getVehicles();
            setVehicles(response.data);

        } catch (error) {

            console.error(error);
            alert("Failed to load vehicles");

        }
    };

    const bookVehicle = (vehicle) => {

        setSelectedVehicle(vehicle);

        setBooking({
            vehicleId: vehicle.id,
            startDate: "",
            endDate: ""
        });

        // Reset amount whenever a new vehicle is selected
        setTotalAmount(0);

    };

    // Calculate total rental amount
    const calculateAmount = (start, end) => {

        if (!start || !end || !selectedVehicle) {
            setTotalAmount(0);
            return;
        }

        const startDate = new Date(start);
        const endDate = new Date(end);

        const days = Math.ceil(
            (endDate - startDate) / (1000 * 60 * 60 * 24)
        );

        if (days > 0) {
            setTotalAmount(days * selectedVehicle.pricePerDay);
        } else {
            setTotalAmount(0);
        }

    };

    const handleChange = (e) => {

        const updatedBooking = {
            ...booking,
            [e.target.name]: e.target.value
        };

        setBooking(updatedBooking);

        calculateAmount(
            updatedBooking.startDate,
            updatedBooking.endDate
        );

    };

    const confirmBooking = async () => {

        if (totalAmount === 0) {
            alert("Please select valid booking dates.");
            return;
        }

        try {

            await createBooking(booking);

            alert("Booking Successful!");

            setSelectedVehicle(null);
            setTotalAmount(0);

            loadVehicles();

        } catch (error) {

            console.error(error);

            alert("Booking Failed!");

        }

    };

    const filteredVehicles = [...vehicles]
        .filter(vehicle =>
            vehicle.vehicleName.toLowerCase().includes(search.toLowerCase())
        )
        .filter(vehicle =>
            typeFilter === "All"
                ? true
                : vehicle.vehicleType === typeFilter
        )
        .sort((a, b) => {

            if (sortOrder === "low") {
                return a.pricePerDay - b.pricePerDay;
            }

            if (sortOrder === "high") {
                return b.pricePerDay - a.pricePerDay;
            }

            return 0;

        });

    return (

        <div className="container mt-4">

            <h2 className="mb-4 text-center">
                Available Vehicles
            </h2>

            <div className="row mb-4">

                <div className="col-md-4">

                    <input
                        className="form-control"
                        placeholder="Search vehicle..."
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />

                </div>

                <div className="col-md-4">

                    <select
                        className="form-select"
                        value={typeFilter}
                        onChange={(e) => setTypeFilter(e.target.value)}
                    >
                        <option>All</option>
                        <option>Bike</option>
                        <option>Car</option>
                        <option>Scooter</option>
                    </select>

                </div>

                <div className="col-md-4">

                    <select
                        className="form-select"
                        value={sortOrder}
                        onChange={(e) => setSortOrder(e.target.value)}
                    >
                        <option value="">Sort by Price</option>
                        <option value="low">Low → High</option>
                        <option value="high">High → Low</option>
                    </select>

                </div>

            </div>

            <div className="row">

                {filteredVehicles.map(vehicle => (

                    <div className="col-md-4 mb-4" key={vehicle.id}>

                        <div className="card shadow h-100">

                            <div className="card-body">

                                <h2 className="text-center mb-3">

                                    {vehicle.vehicleType === "Bike" && "🏍️"}

                                    {vehicle.vehicleType === "Car" && "🚗"}

                                    {vehicle.vehicleType === "Scooter" && "🛵"}

                                </h2>

                                <h5>{vehicle.vehicleName}</h5>

                                <p>
                                    <strong>Type:</strong> {vehicle.vehicleType}
                                </p>

                                <p>
                                    <strong>Model:</strong> {vehicle.model}
                                </p>

                                <p>
                                    <strong>Number Plate:</strong> {vehicle.numberPlate}
                                </p>

                                <p>
                                    <strong>Price:</strong> ₹{vehicle.pricePerDay}/day
                                </p>

                                <p>
                                    {vehicle.available
                                        ? "🟢 Available"
                                        : "🔴 Not Available"}
                                </p>

                                {vehicle.available ? (

                                    <button
                                        className="btn btn-success w-100"
                                        onClick={() => bookVehicle(vehicle)}
                                    >
                                        Book Now
                                    </button>

                                ) : (

                                    <button
                                        className="btn btn-secondary w-100"
                                        disabled
                                    >
                                        Unavailable
                                    </button>

                                )}

                            </div>

                        </div>

                    </div>

                ))}

            </div>

            {selectedVehicle && (

                <div className="card mt-4 shadow p-4">

                    <h4 className="mb-3">
                        Book {selectedVehicle.vehicleName}
                    </h4>

                    <div className="mb-3">

                        <label className="form-label">
                            Start Date
                        </label>

                        <input
                            type="date"
                            name="startDate"
                            className="form-control"
                            value={booking.startDate}
                            onChange={handleChange}
                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">
                            End Date
                        </label>

                        <input
                            type="date"
                            name="endDate"
                            className="form-control"
                            value={booking.endDate}
                            onChange={handleChange}
                        />

                    </div>

                    <div className="alert alert-info text-center">

                        <h5>
                            Total Amount:
                            <strong> ₹{totalAmount}</strong>
                        </h5>

                    </div>

                    <button
                        className="btn btn-primary me-2"
                        onClick={confirmBooking}
                    >
                        Confirm Booking
                    </button>

                    <button
                        className="btn btn-secondary mt-2"
                        onClick={() => {
                            setSelectedVehicle(null);
                            setTotalAmount(0);
                        }}
                    >
                        Cancel
                    </button>

                </div>

            )}

        </div>

    );

}

export default Vehicles;