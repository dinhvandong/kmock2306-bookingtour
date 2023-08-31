package com.vti.booking_tour.database;


import com.vti.booking_tour.entities.Role;
import com.vti.booking_tour.models.ERole;
import com.vti.booking_tour.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository){
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
            }
        };
    }
}
