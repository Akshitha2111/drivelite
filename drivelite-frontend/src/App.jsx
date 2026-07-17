import { BrowserRouter, Routes, Route } from "react-router-dom";


import Navbar from "./components/Navbar";


import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";

import Vehicles from "./pages/Vehicles";
import Dashboard from "./pages/Dashboard";
import MyBookings from "./pages/MyBookings";

import AdminDashboard from "./pages/AdminDashboard";
import AdminVehicles from "./pages/AdminVehicles";


import ProtectedRoute from "./components/ProtectedRoute";

import Recommendation from "./pages/Recommendation";



function App() {


    return (


        <BrowserRouter>


            <Navbar />



            <Routes>



                {/* PUBLIC ROUTES */}


                <Route

                    path="/"

                    element={<Home />}

                />



                <Route

                    path="/login"

                    element={<Login />}

                />



                <Route

                    path="/register"

                    element={<Register />}

                />





                {/* USER ROUTES */}



                <Route

                    path="/dashboard"

                    element={

                        <ProtectedRoute role="USER">

                            <Dashboard />

                        </ProtectedRoute>

                    }

                />





                <Route

                    path="/vehicles"

                    element={

                        <ProtectedRoute role="USER">

                            <Vehicles />

                        </ProtectedRoute>

                    }

                />





                <Route

                    path="/my-bookings"

                    element={

                        <ProtectedRoute role="USER">

                            <MyBookings />

                        </ProtectedRoute>

                    }

                />

                <Route

                    path="/recommend"

                    element={

                        <ProtectedRoute role="USER">

                            <Recommendation />

                        </ProtectedRoute>

                    }

                />





                {/* ADMIN ROUTES */}




                <Route

                    path="/admin"

                    element={

                        <ProtectedRoute role="ADMIN">

                            <AdminDashboard />

                        </ProtectedRoute>

                    }

                />





                <Route

                    path="/admin/vehicles"

                    element={

                        <ProtectedRoute role="ADMIN">

                            <AdminVehicles />

                        </ProtectedRoute>

                    }

                />




            </Routes>


        </BrowserRouter>


    );


}


export default App;