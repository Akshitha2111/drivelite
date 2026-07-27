package com.drivelite.dto;

public class DashboardResponse {

    private long totalUsers;
    private long totalVehicles;
    private long availableVehicles;
    private long bookedVehicles;
    private long totalBookings;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getAvailableVehicles() {
        return availableVehicles;
    }

    public void setAvailableVehicles(long availableVehicles) {
        this.availableVehicles = availableVehicles;
    }

    public long getBookedVehicles() {
        return bookedVehicles;
    }

    public void setBookedVehicles(long bookedVehicles) {
        this.bookedVehicles = bookedVehicles;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }
}