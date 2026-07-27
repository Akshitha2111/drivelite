package com.drivelite.config;


import com.drivelite.model.User;
import com.drivelite.model.Vehicle;

import com.drivelite.repository.UserRepository;
import com.drivelite.repository.VehicleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;



@Component
public class DataLoader implements CommandLineRunner {


    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public DataLoader(
            VehicleRepository vehicleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){

        this.vehicleRepository=vehicleRepository;

        this.userRepository=userRepository;

        this.passwordEncoder=passwordEncoder;

    }



    @Override
    public void run(String... args) {


        createAdmin();


        loadVehicles();


    }



    private void createAdmin(){


        if(userRepository.findByEmail("admin@drivelite.com").isEmpty()){


            User admin=new User();


            admin.setName("DriveLite Admin");

            admin.setEmail("admin@drivelite.com");

            admin.setPhone("9999999999");

            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );


            admin.setRole("ADMIN");


            userRepository.save(admin);



            System.out.println(
                    "========== ADMIN CREATED =========="
            );

            System.out.println(
                    "Email : admin@drivelite.com"
            );

            System.out.println(
                    "Password : admin123"
            );

        }


    }




    private void loadVehicles(){


        if(vehicleRepository.count()>0){

            return;

        }


        addVehicle("Honda Activa 6G","Scooter","2025","AP39SC1001",500,true);
        addVehicle("TVS Jupiter","Scooter","2025","AP39SC1002",450,true);
        addVehicle("Suzuki Access 125","Scooter","2025","AP39SC1003",550,true);
        addVehicle("Hero Pleasure+","Scooter","2024","AP39SC1004",400,true);


        addVehicle("Hero Splendor Plus","Bike","2025","AP39BK1001",650,true);
        addVehicle("Honda Shine","Bike","2025","AP39BK1002",700,true);
        addVehicle("Bajaj Pulsar NS200","Bike","2025","AP39BK1003",1200,true);
        addVehicle("Yamaha R15 V4","Bike","2025","AP39BK1004",1800,true);
        addVehicle("Royal Enfield Classic 350","Bike","2025","AP39BK1005",1600,true);
        addVehicle("KTM Duke 390","Bike","2025","AP39BK1006",2000,true);


        addVehicle("Maruti Swift","Car","2025","AP39CR1001",2200,true);
        addVehicle("Hyundai i20","Car","2025","AP39CR1002",2500,true);
        addVehicle("Honda City","Car","2025","AP39CR1003",3000,true);
        addVehicle("Hyundai Verna","Car","2025","AP39CR1004",3200,true);


        addVehicle("Tata Nexon","SUV","2025","AP39SV1001",3500,true);
        addVehicle("Kia Seltos","SUV","2025","AP39SV1002",3800,true);
        addVehicle("Mahindra XUV700","SUV","2025","AP39SV1003",5000,true);
        addVehicle("Toyota Innova Hycross","SUV","2025","AP39SV1004",5500,true);
        addVehicle("Toyota Fortuner","SUV","2025","AP39SV1005",7000,true);
        addVehicle("MG Hector","SUV","2025","AP39SV1006",4500,true);



        System.out.println(
                "========== Demo Vehicles Loaded =========="
        );

    }





    private void addVehicle(
            String name,
            String type,
            String model,
            String plate,
            double price,
            boolean available
    ){


        Vehicle vehicle=new Vehicle();


        vehicle.setVehicleName(name);

        vehicle.setVehicleType(type);

        vehicle.setModel(model);

        vehicle.setNumberPlate(plate);

        vehicle.setPricePerDay(price);

        vehicle.setAvailable(available);



        vehicleRepository.save(vehicle);


    }


}