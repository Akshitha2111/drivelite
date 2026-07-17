import { Link, useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const token = localStorage.getItem("token");

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("name");
        localStorage.removeItem("email");

        alert("Logged out successfully!");

        navigate("/login");
    };

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">

            <Link className="navbar-brand fs-3 fw-bold" to="/">
                DriveLite
            </Link>

            <div className="ms-auto">

                <Link className="btn btn-outline-light mx-2" to="/">
                    Home
                </Link>

                <Link className="btn btn-outline-light mx-2" to="/vehicles">
                    Vehicles
                </Link>

                {!token ? (
                    <>
                        <Link
                            className="btn btn-outline-light mx-2"
                            to="/login"
                        >
                            Login
                        </Link>

                        <Link
                            className="btn btn-warning mx-2"
                            to="/register"
                        >
                            Register
                        </Link>
                    </>
                ) : (
                    <>
                        <Link
                            className="btn btn-outline-light mx-2"
                            to="/dashboard"
                        >
                            Dashboard
                        </Link>

                        <Link
                            className="btn btn-outline-light mx-2"
                            to="/recommend"
                        >
                            AI Recommend
                        </Link>

                        <Link
                            className="btn btn-outline-light mx-2"
                            to="/my-bookings"
                        >
                            My Bookings
                        </Link>

                        <button
                            className="btn btn-danger mx-2"
                            onClick={logout}
                        >
                            Logout
                        </button>
                    </>
                )}

            </div>

        </nav>

    );
}

export default Navbar;