package com.vti.booking_tour.services;


import com.vti.booking_tour.entities.Booking;
import com.vti.booking_tour.models.BookingStatus;
import com.vti.booking_tour.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
     final BookingRepository bookingRepository;
     @Autowired
     public BookingService(BookingRepository bookingRepository){
         this.bookingRepository = bookingRepository;
     }

     public Booking insert(Booking booking){

         return bookingRepository.save(booking);
     }

     public Booking update(Booking booking){
         Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());
         if(optionalBooking.isPresent()){
             return bookingRepository.save(booking);
         }
         return  null;
     }

     public Booking delete(Long id){
         Optional<Booking> optionalBooking = bookingRepository.findById(id);
         if(optionalBooking.isPresent()){
             Booking foundBooking = optionalBooking.get();
             if(foundBooking.getStatus()== BookingStatus.BOOKING_PENDING){
                 foundBooking.setStatus(BookingStatus.BOOKING_CANCEL);
                 return bookingRepository.save(foundBooking);
             }
         }
         return null;
     }

     public List<Booking> findAll()
     {
         return bookingRepository.findAll();
     }

     public List<Booking> findAllBookingActive()
     {
         // GOING  PENDING
         return null;
     }

    public List<Booking> findAllBookingCancel()
    {

        return null;
    }


    public List<Booking> findAllBookingFinish()
    {

        return null;
    }
}
