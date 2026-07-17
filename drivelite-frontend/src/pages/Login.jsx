import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";


function Login() {


    const navigate = useNavigate();


    const [login, setLogin] = useState({

        email: "",

        password: ""

    });



    const handleChange = (e) => {

        setLogin({

            ...login,

            [e.target.name]: e.target.value

        });

    };




    const handleSubmit = async (e) => {


        e.preventDefault();



        try {


            const response =
                await api.post("/auth/login", login);



            console.log("LOGIN RESPONSE:");
            console.log(response.data);



            localStorage.setItem(
                "token",
                response.data.token
            );


            localStorage.setItem(
                "name",
                response.data.name || ""
            );


            localStorage.setItem(
                "email",
                response.data.email || ""
            );


            localStorage.setItem(
                "role",
                response.data.role
            );



            console.log(
                "ROLE:",
                response.data.role
            );



            alert("Login Successful!");




            if(response.data.role === "ADMIN"){


                navigate("/admin");


            }
            else{


                navigate("/dashboard");


            }



        }
        catch(error){


            console.error(
                "LOGIN ERROR:",
                error
            );


            alert(
                "Invalid Email or Password"
            );


        }


    };




    return (


        <div style={{padding:"30px"}}>


            <h2>
                Login
            </h2>



            <form onSubmit={handleSubmit}>


                <input

                    type="email"

                    name="email"

                    placeholder="Enter Email"

                    value={login.email}

                    onChange={handleChange}

                />



                <br/>
                <br/>



                <input

                    type="password"

                    name="password"

                    placeholder="Enter Password"

                    value={login.password}

                    onChange={handleChange}

                />



                <br/>
                <br/>



                <button type="submit">

                    Login

                </button>



            </form>


        </div>


    );


}


export default Login;