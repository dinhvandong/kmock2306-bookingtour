package com.vti.booking_tour.repositories;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.vti.booking_tour.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPhone(String phone);
    Optional<Booking> findByUsername(String username);

}
