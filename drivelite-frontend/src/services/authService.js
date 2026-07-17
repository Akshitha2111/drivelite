import api from "./api";

export const login = (data) => {
    return api.post("/auth/login", data);
};

export const register = (data) => {
    return api.post("/users/register", data);
};

export const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
};