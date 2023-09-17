package com.vti.booking_tour.controllers;

import com.vti.booking_tour.entities.Booking;
import com.vti.booking_tour.models.ResponseObject;
import com.vti.booking_tour.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:3000"},maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject(200, "OK", bookingService.findAllBookingActive() ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.insert(booking);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "OK", createdBooking));
    }

    @PutMapping
    public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking) {
        Optional<Booking> existingBooking = bookingService.getBookingById(booking.getId());
        if (existingBooking.isPresent()) {
            Booking updatedBooking = bookingService.update(booking);
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        Optional<Booking> existingBooking = bookingService.getBookingById(id);
        if (existingBooking.isPresent()) {
            bookingService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
