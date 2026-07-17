package com.drivelite.dto;


public class AdminDashboardResponse {


    private long totalVehicles;

    private long availableVehicles;

    private long totalUsers;


    public AdminDashboardResponse(
            long totalVehicles,
            long availableVehicles,
            long totalUsers
    ){

        this.totalVehicles = totalVehicles;
        this.availableVehicles = availableVehicles;
        this.totalUsers = totalUsers;

    }



    public long getTotalVehicles(){

        return totalVehicles;

    }



    public long getAvailableVehicles(){

        return availableVehicles;

    }



    public long getTotalUsers(){

        return totalUsers;

    }

}