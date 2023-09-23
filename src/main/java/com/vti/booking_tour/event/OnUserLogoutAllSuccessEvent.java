package com.vti.booking_tour.event;

//import com.california.propertymanagement.dto.LogOutAllDeviceRequest;
import com.vti.booking_tour.dto.LogOutAllDeviceRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class OnUserLogoutAllSuccessEvent extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String userName;
    private final transient LogOutAllDeviceRequest logOutRequest;
    private final Date eventTime;



    public OnUserLogoutAllSuccessEvent(String userName,  LogOutAllDeviceRequest logOutRequest) {
        super(userName);
        this.userName = userName;
        this.logOutRequest = logOutRequest;
        this.eventTime = Date.from(Instant.now());
    }

}