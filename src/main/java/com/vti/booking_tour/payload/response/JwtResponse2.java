package com.vti.booking_tour.payload.response;

import com.vti.booking_tour.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse2 {

    String jwtToken;
    String token;
    long expiryDuration;
    Long id;
    Set<Role> roles = new HashSet<>();
    @NotBlank @Size(min = 3, max = 100) String username,  email;

}
