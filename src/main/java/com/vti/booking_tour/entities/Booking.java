package com.vti.booking_tour.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.booking_tour.models.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String code;
    private String username;
    private String phone;
    private String bookingName;
    private double price;

    // link image booking
    private String url;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  finishDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  createdDate;
    private String destination;
    private String departure;
    private int status = BookingStatus.BOOKING_PENDING;

    public boolean isActive()
    {
        return status == BookingStatus.BOOKING_PENDING
                || status == BookingStatus.BOOKING_GOING;
    }

    public boolean isCancel()
    {
        return status == BookingStatus.BOOKING_CANCEL;
    }

    public  boolean isFinish()
    {
        return status == BookingStatus.BOOKING_FINISH;
    }



}
