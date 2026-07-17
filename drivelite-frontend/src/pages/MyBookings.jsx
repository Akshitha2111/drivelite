import { useEffect, useState } from "react";
import { getMyBookings, cancelBooking } from "../services/bookingService";

function MyBookings() {

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

            alert("Failed to load bookings");

        }

    };

    const handleCancel = async (id) => {

        const confirm = window.confirm(
            "Are you sure you want to cancel this booking?"
        );

        if (!confirm) return;

        try {

            await cancelBooking(id);

            alert("Booking Cancelled Successfully!");

            loadBookings();

        } catch (error) {

            console.error(error);

            alert("Failed to cancel booking");

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">My Bookings</h2>

            <table className="table table-bordered table-striped">

                <thead className="table-dark">

                <tr>
                    <th>ID</th>
                    <th>Vehicle</th>
                    <th>Type</th>
                    <th>Model</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                </thead>

                <tbody>

                {bookings.map((booking) => (

                    <tr key={booking.id}>

                        <td>{booking.id}</td>

                        <td>{booking.vehicleName}</td>

                        <td>{booking.vehicleType}</td>

                        <td>{booking.model}</td>

                        <td>{booking.startDate}</td>

                        <td>{booking.endDate}</td>

                        <td>₹{booking.totalAmount}</td>

                        <td>

                            {booking.status === "BOOKED" &&
                                <span className="badge bg-success">
                                    BOOKED
                                </span>
                            }

                            {booking.status === "CANCELLED" &&
                                <span className="badge bg-danger">
                                    CANCELLED
                                </span>
                            }

                            {booking.status === "COMPLETED" &&
                                <span className="badge bg-primary">
                                    COMPLETED
                                </span>
                            }

                        </td>

                        <td>

                            {booking.status === "BOOKED" && (

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => handleCancel(booking.id)}
                                >
                                    Cancel
                                </button>

                            )}

                        </td>

                    </tr>

                ))}

                </tbody>

            </table>

        </div>

    );

}

export default MyBookings;