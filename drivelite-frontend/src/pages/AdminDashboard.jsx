import React, { useEffect, useState } from "react";
import api from "../services/api";

import {
    getVehicles,
    createVehicle,
    updateVehicle,
    deleteVehicle
} from "../services/vehicleService";


function AdminDashboard() {


    const emptyVehicle = {

        vehicleName: "",
        vehicleType: "",
        model: "",
        numberPlate: "",
        pricePerDay: "",
        available: true,
        imageUrl: ""

    };



    const [dashboard, setDashboard] = useState({});

    const [vehicles, setVehicles] = useState([]);

    const [users, setUsers] = useState([]);


    const [vehicle, setVehicle] = useState(emptyVehicle);

    const [editId, setEditId] = useState(null);




    useEffect(() => {

        loadDashboard();
        loadVehicles();
        loadUsers();

    }, []);





    const loadDashboard = async () => {

        try {

            const response =
                await api.get("/admin/dashboard");

            setDashboard(response.data);


        } catch(error) {

            console.error(
                "Dashboard Error:",
                error
            );

        }

    };






    const loadVehicles = async () => {

        try {

            const response =
                await getVehicles();

            setVehicles(response.data);


        } catch(error) {

            console.error(
                "Vehicle Error:",
                error
            );

        }

    };






    const loadUsers = async () => {

        try {

            const response =
                await api.get("/admin/users");

            setUsers(response.data);


        } catch(error) {

            console.error(
                "User Error:",
                error
            );

        }

    };







    const handleChange = (e) => {


        setVehicle({

            ...vehicle,

            [e.target.name]: e.target.value

        });


    };







    const submitVehicle = async (e) => {


        e.preventDefault();


        try {


            if(editId !== null) {


                await updateVehicle(
                    editId,
                    vehicle
                );


                alert(
                    "Vehicle Updated Successfully"
                );


            }
            else {


                await createVehicle(vehicle);


                alert(
                    "Vehicle Added Successfully"
                );


            }



            setVehicle(emptyVehicle);

            setEditId(null);


            loadVehicles();


        }
        catch(error) {


            console.error(error);


            alert(
                "Operation Failed"
            );


        }


    };









    const editVehicle = (v) => {

        console.log("EDIT CLICKED");
        console.log("SELECTED VEHICLE:", v);


        setVehicle({

            vehicleName: v.vehicleName || "",

            vehicleType: v.vehicleType || "",

            model: v.model || "",

            numberPlate: v.numberPlate || "",

            pricePerDay: v.pricePerDay || "",

            available: v.available,

            imageUrl: v.imageUrl || ""

        });


        setEditId(v.id);


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    };









    const removeVehicle = async(id)=>{


        if(!window.confirm(
            "Delete this vehicle?"
        )) {

            return;

        }



        try {


            await deleteVehicle(id);


            alert(
                "Vehicle Deleted"
            );


            loadVehicles();



        }
        catch(error) {


            console.error(error);


        }


    };








    return (

        <div className="container mt-4">



            <h1>
                DriveLite Admin Dashboard
            </h1>





            <div className="cards">


                <h3>
                    Total Vehicles :
                    {dashboard.totalVehicles}
                </h3>



                <h3>
                    Available Vehicles :
                    {dashboard.availableVehicles}
                </h3>



                <h3>
                    Total Users :
                    {dashboard.totalUsers}
                </h3>


            </div>







            <hr/>




            <h2>
                Vehicle Management
            </h2>





            <form
                onSubmit={submitVehicle}
            >



                <input

                    className="form-control mb-2"

                    name="vehicleName"

                    placeholder="Vehicle Name"

                    value={vehicle.vehicleName}

                    onChange={handleChange}

                />




                <input

                    className="form-control mb-2"

                    name="vehicleType"

                    placeholder="Vehicle Type"

                    value={vehicle.vehicleType}

                    onChange={handleChange}

                />




                <input

                    className="form-control mb-2"

                    name="model"

                    placeholder="Model"

                    value={vehicle.model}

                    onChange={handleChange}

                />




                <input

                    className="form-control mb-2"

                    name="numberPlate"

                    placeholder="Number Plate"

                    value={vehicle.numberPlate}

                    onChange={handleChange}

                />





                <input

                    className="form-control mb-2"

                    name="pricePerDay"

                    placeholder="Price Per Day"

                    value={vehicle.pricePerDay}

                    onChange={handleChange}

                />





                <input

                    className="form-control mb-2"

                    name="imageUrl"

                    placeholder="Image URL"

                    value={vehicle.imageUrl}

                    onChange={handleChange}

                />






                <button
                    className="btn btn-primary"
                >

                    {
                        editId !== null
                            ?
                            "Update Vehicle"
                            :
                            "Add Vehicle"
                    }

                </button>



            </form>







            <hr/>






            <h2>
                Vehicles
            </h2>






            <table className="table table-bordered">


                <thead>

                <tr>

                    <th>Name</th>

                    <th>Type</th>

                    <th>Price</th>

                    <th>Status</th>

                    <th>Action</th>


                </tr>

                </thead>





                <tbody>


                {

                    vehicles.map(v=>(


                        <tr key={v.id}>


                            <td>
                                {v.vehicleName}
                            </td>


                            <td>
                                {v.vehicleType}
                            </td>


                            <td>
                                ₹{v.pricePerDay}
                            </td>


                            <td>

                                {
                                    v.available
                                        ?
                                        "Available"
                                        :
                                        "Not Available"
                                }

                            </td>



                            <td>


                                <button

                                    className="btn btn-warning me-2"

                                    onClick={() =>
                                        editVehicle(v)
                                    }

                                >

                                    Edit

                                </button>




                                <button

                                    className="btn btn-danger"

                                    onClick={() =>
                                        removeVehicle(v.id)
                                    }

                                >

                                    Delete

                                </button>


                            </td>



                        </tr>


                    ))

                }



                </tbody>


            </table>








            <hr/>






            <h2>
                Users
            </h2>






            <table className="table table-bordered">


                <thead>

                <tr>

                    <th>Name</th>

                    <th>Email</th>

                    <th>Role</th>


                </tr>

                </thead>





                <tbody>


                {

                    users.map(user=>(


                        <tr key={user.id}>


                            <td>
                                {user.name}
                            </td>


                            <td>
                                {user.email}
                            </td>


                            <td>
                                {user.role}
                            </td>



                        </tr>


                    ))

                }



                </tbody>


            </table>





        </div>


    );


}


export default AdminDashboard;