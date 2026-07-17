import { Link } from "react-router-dom";

function Home() {
    return (
        <div className="container text-center mt-5">

            <h1 className="display-4 fw-bold">
                Welcome to DriveLite 🚗
            </h1>

            <p className="lead mt-3">
                Rent vehicles easily and travel anywhere.
            </p>

            <div className="mt-4">
                <Link to="/vehicles" className="btn btn-primary btn-lg me-3">
                    View Vehicles
                </Link>

                <Link to="/login" className="btn btn-success btn-lg">
                    Login
                </Link>
            </div>

        </div>
    );
}

export default Home;