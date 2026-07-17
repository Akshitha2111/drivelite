import api from "./api";

export const createBooking = (booking) => {
    return api.post("/bookings", booking);
};

export const getBookings = () => {
    return api.get("/bookings");
};

export const getMyBookings = () => {
    return api.get("/bookings/my");
};

export const cancelBooking = (id) => {
    return api.put(`/bookings/${id}/cancel`);
};

export const completeBooking = (id) => {
    return api.put(`/bookings/${id}/complete`);
};