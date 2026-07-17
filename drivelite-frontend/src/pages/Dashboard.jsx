import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getMyBookings } from "../services/bookingService";

function Dashboard() {

    const navigate = useNavigate();

    const name = localStorage.getItem("name");
    const email = localStorage.getItem("email");

    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        loadBookings();
    }, []);

    const loadBookings = async () => {

        try {

            const response = await getMyBookings();

            setBookings(response.data);

        } catch (error) {

            console.error(error);

        }

    };

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("name");
        localStorage.removeItem("email");

        alert("Logged out successfully!");

        navigate("/login");
    };

    const totalBookings = bookings.length;

    const booked = bookings.filter(
        booking => booking.status === "BOOKED"
    ).length;

    const completed = bookings.filter(
        booking => booking.status === "COMPLETED"
    ).length;

    const cancelled = bookings.filter(
        booking => booking.status === "CANCELLED"
    ).length;

    return (

        <div className="container mt-5">

            <div className="card shadow p-4">

                <h2>Welcome, {name} 👋</h2>

                <p className="text-muted">{email}</p>

                <hr />

                <div className="row text-center">

                    <div className="col-md-3 mb-3">

                        <div className="card bg-primary text-white">

                            <div className="card-body">

                                <h5>Total Bookings</h5>

                                <h2>{totalBookings}</h2>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-3 mb-3">

                        <div className="card bg-success text-white">

                            <div className="card-body">

                                <h5>Booked</h5>

                                <h2>{booked}</h2>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-3 mb-3">

                        <div className="card bg-info text-white">

                            <div className="card-body">

                                <h5>Completed</h5>

                                <h2>{completed}</h2>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-3 mb-3">

                        <div className="card bg-danger text-white">

                            <div className="card-body">

                                <h5>Cancelled</h5>

                                <h2>{cancelled}</h2>

                            </div>

                        </div>

                    </div>

                </div>

                <hr />

                <div className="d-flex gap-3">

                    <button
                        className="btn btn-primary"
                        onClick={() => navigate("/vehicles")}
                    >
                        View Vehicles
                    </button>

                    <button
                        className="btn btn-success"
                        onClick={() => navigate("/my-bookings")}
                    >
                        My Bookings
                    </button>

                    <button
                        className="btn btn-danger ms-auto"
                        onClick={logout}
                    >
                        Logout
                    </button>

                </div>

            </div>

        </div>

    );

}

export default Dashboard;