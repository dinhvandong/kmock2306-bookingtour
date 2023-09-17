package com.vti.booking_tour.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://example.com") // Replace with the allowed origin(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify the allowed HTTP methods
                .allowedHeaders("Content-Type", "Authorization") // Specify the allowed headers
                .allowCredentials(true) // Enable credentials (e.g., cookies)
                .maxAge(3600);
    }
}
