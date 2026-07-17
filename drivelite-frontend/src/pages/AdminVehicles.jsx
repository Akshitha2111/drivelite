import { useEffect, useState } from "react";

import {
    getVehicles,
    createVehicle,
    updateVehicle,
    deleteVehicle
} from "../services/vehicleService";


function AdminVehicles() {


    const emptyVehicle = {

        vehicleName:"",
        vehicleType:"",
        model:"",
        numberPlate:"",
        pricePerDay:"",
        available:true,
        imageUrl:""

    };


    const [vehicles,setVehicles] = useState([]);

    const [vehicle,setVehicle] = useState(emptyVehicle);

    const [editId,setEditId] = useState(null);



    useEffect(()=>{

        loadVehicles();

    },[]);



    const loadVehicles = async()=>{

        try{

            const response = await getVehicles();

            setVehicles(response.data);

        }
        catch(error){

            console.error(error);

        }

    };



    const handleChange=(e)=>{


        setVehicle({

            ...vehicle,

            [e.target.name]: e.target.value

        });


    };



    const submitVehicle=async(e)=>{


        e.preventDefault();


        try{


            if(editId){

                await updateVehicle(editId,vehicle);

                alert("Vehicle Updated");

            }
            else{

                await createVehicle(vehicle);

                alert("Vehicle Added");

            }


            setVehicle(emptyVehicle);

            setEditId(null);

            loadVehicles();


        }
        catch(error){

            console.error(error);

            alert("Operation Failed");

        }


    };



    const editVehicle=(v)=>{


        setVehicle(v);

        setEditId(v.id);


    };



    const removeVehicle=async(id)=>{


        if(!window.confirm("Delete vehicle?"))
            return;


        try{

            await deleteVehicle(id);

            alert("Vehicle Deleted");

            loadVehicles();

        }
        catch(error){

            console.error(error);

        }


    };



    return(


        <div className="container mt-4">


            <h2>
                Manage Vehicles
            </h2>



            <form onSubmit={submitVehicle}>


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


                <button className="btn btn-primary">

                    {
                        editId
                            ?
                            "Update Vehicle"
                            :
                            "Add Vehicle"
                    }

                </button>


            </form>



            <hr/>




            <table className="table table-bordered">


                <thead>


                <tr>

                    <th>Name</th>
                    <th>Type</th>
                    <th>Price</th>
                    <th>Actions</th>

                </tr>


                </thead>



                <tbody>


                {
                    vehicles.map(v=>(


                        <tr key={v.id}>


                            <td>{v.vehicleName}</td>

                            <td>{v.vehicleType}</td>

                            <td>₹{v.pricePerDay}</td>


                            <td>


                                <button

                                    className="btn btn-warning me-2"

                                    onClick={()=>editVehicle(v)}

                                >

                                    Edit

                                </button>



                                <button

                                    className="btn btn-danger"

                                    onClick={()=>removeVehicle(v.id)}

                                >

                                    Delete

                                </button>


                            </td>


                        </tr>


                    ))
                }


                </tbody>


            </table>



        </div>


    );


}


export default AdminVehicles;