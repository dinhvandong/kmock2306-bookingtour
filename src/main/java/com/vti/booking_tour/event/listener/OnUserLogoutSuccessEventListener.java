package com.vti.booking_tour.event.listener;

//
//import com.california.propertymanagement.cache.LoggedOutJwtTokenCache;
//import com.california.propertymanagement.dto.DeviceInfo;
//import com.california.propertymanagement.event.OnUserLogoutSuccessEvent;
import com.vti.booking_tour.cache.LoggedOutJwtTokenCache;
import com.vti.booking_tour.dto.DeviceInfo;
import com.vti.booking_tour.event.OnUserLogoutSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private final LoggedOutJwtTokenCache tokenCache;
    private static final Logger logger = LoggerFactory.getLogger(OnUserLogoutSuccessEventListener.class);

    @Autowired
    public OnUserLogoutSuccessEventListener(LoggedOutJwtTokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        if (null != event) {
            DeviceInfo deviceInfo = event.getLogOutRequest().getDeviceInfo();
            logger.info(String.format("Log out success event received for user [%s] for device [%s]", event.getUserName(), deviceInfo));
            tokenCache.markLogoutEventForToken(event);
        }
    }
}
