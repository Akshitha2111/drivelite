import { Navigate } from "react-router-dom";


function ProtectedRoute({ children, role }) {


    const token = localStorage.getItem("token");

    const userRole = localStorage.getItem("role");



    // No token → go to login

    if (!token) {

        return (
            <Navigate
                to="/login"
                replace
            />
        );

    }



    // Role checking

    if (role && userRole !== role) {


        if (userRole === "ADMIN") {

            return (
                <Navigate
                    to="/admin"
                    replace
                />
            );

        }


        return (
            <Navigate
                to="/dashboard"
                replace
            />
        );


    }



    return children;


}


export default ProtectedRoute;