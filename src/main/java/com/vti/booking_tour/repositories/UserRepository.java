package com.vti.booking_tour.repositories;

import com.vti.booking_tour.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
//    Page<User> findAllByParentID (long parentID, Pageable pageable);
//    List<User> findAllByParentID (long parentID);
    Boolean existsByUsername(String userName);
}