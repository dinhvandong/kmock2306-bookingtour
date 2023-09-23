package com.vti.booking_tour.database;


import com.vti.booking_tour.entities.Role;
import com.vti.booking_tour.entities.User;
import com.vti.booking_tour.models.ERole;
import com.vti.booking_tour.repositories.RoleRepository;
import com.vti.booking_tour.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class Database {

    @Autowired
    PasswordEncoder encoder;
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Khoi tao cac gia tri o day
                Role role1 = new Role();
                role1.setId(1);
                role1.setName(ERole.ROLE_ADMIN);

                Role role2 = new Role();
                role2.setId(2);
                role2.setName(ERole.ROLE_MANAGER);
                Role role3 = new Role();
                role3.setId(3);
                role3.setName(ERole.ROLE_RECEPTIONIST);

                Role role4 = new Role();
                role4.setId(4);
                role4.setName(ERole.ROLE_SALE);

                Role role5 = new Role();
                role5.setId(5);
                role5.setName(ERole.ROLE_CLIENT);
                if(roleRepository.findAll().size() == 0)
                {
                    roleRepository.save(role1);
                    roleRepository.save(role2);
                    roleRepository.save(role3);
                    roleRepository.save(role4);
                    roleRepository.save(role5);
                }

                if(userRepository.findAll().size()==0){
                    User admin = new User();
                    admin.setId(1l);
                    admin.setUsername("admin");
                    admin.setEmail("admin@gmail.com");
                    admin.setCreatedDate(LocalDateTime.now());
                    Set<Role> roles = new HashSet<>();
                    admin.setPassword(encoder.encode("A123456a@"));
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    admin.setRoles(roles);
                    userRepository.save(admin);
                }
            }
        };
    }
}
