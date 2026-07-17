import { useState } from "react";
import api from "../services/api";

function Register() {

    const [user, setUser] = useState({
        name: "",
        email: "",
        phone: "",
        password: ""
    });

    const handleChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await api.post("/users/register", user);

            alert("Registration Successful!");

            console.log(response.data);

            setUser({
                name: "",
                email: "",
                phone: "",
                password: ""
            });

        } catch (error) {

            console.error(error);

            alert("Registration Failed!");

        }

    };

    return (

        <div style={{ padding: "30px" }}>

            <h2>Register</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="text"
                    name="name"
                    placeholder="Enter Name"
                    value={user.name}
                    onChange={handleChange}
                />

                <br /><br />

                <input
                    type="email"
                    name="email"
                    placeholder="Enter Email"
                    value={user.email}
                    onChange={handleChange}
                />

                <br /><br />

                <input
                    type="text"
                    name="phone"
                    placeholder="Enter Phone Number"
                    value={user.phone}
                    onChange={handleChange}
                />

                <br /><br />

                <input
                    type="password"
                    name="password"
                    placeholder="Enter Password"
                    value={user.password}
                    onChange={handleChange}
                />

                <br /><br />

                <button type="submit">
                    Register
                </button>

            </form>

        </div>

    );

}

export default Register;