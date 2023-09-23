package com.vti.booking_tour.repositories;

import com.vti.booking_tour.entities.Role;
import com.vti.booking_tour.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}

