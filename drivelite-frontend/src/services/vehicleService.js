import api from "./api";


// ================= USER VEHICLE APIs =================


// Get all vehicles
export const getVehicles = () => {
    return api.get("/vehicles");
};


// Get available vehicles
export const getAvailableVehicles = () => {
    return api.get("/vehicles/available");
};


// Get vehicles by type
export const getVehicleByType = (type) => {
    return api.get(`/vehicles/type/${type}`);
};


// Get vehicles by price range
export const getVehiclesByPrice = (min, max) => {
    return api.get(`/vehicles/price?min=${min}&max=${max}`);
};



// ================= ADMIN VEHICLE CRUD APIs =================


// Add vehicle
export const createVehicle = (vehicle) => {
    return api.post("/vehicles", vehicle);
};


// Update vehicle
export const updateVehicle = (id, vehicle) => {
    return api.put(`/vehicles/${id}`, vehicle);
};


// Delete vehicle
export const deleteVehicle = (id) => {
    return api.delete(`/vehicles/${id}`);
};