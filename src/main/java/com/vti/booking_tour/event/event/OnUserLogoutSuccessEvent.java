package com.vti.booking_tour.event.event;

import com.vti.booking_tour.dto.LogOutRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String userName;
    private final String token;
    private final transient LogOutRequest logOutRequest;
    private final Date eventTime;



    public OnUserLogoutSuccessEvent(String userName, String token, LogOutRequest logOutRequest) {
        super(userName);
        this.userName = userName;
        this.token = token;
        this.logOutRequest = logOutRequest;
        this.eventTime = Date.from(Instant.now());
    }

}
