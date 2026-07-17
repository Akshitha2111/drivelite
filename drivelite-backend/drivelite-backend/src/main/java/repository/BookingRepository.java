package com.drivelite.repository;

import com.drivelite.model.Booking;
import com.drivelite.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.vehicle.id = :vehicleId
            AND b.status = 'BOOKED'
            AND (
                b.startDate < :endDate
                AND b.endDate > :startDate
            )
            """)
    boolean existsOverlappingBooking(
            @Param("vehicleId") Integer vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Booking> findByUser(User user);

}