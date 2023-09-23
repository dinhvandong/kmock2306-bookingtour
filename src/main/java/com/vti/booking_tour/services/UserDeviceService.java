package com.vti.booking_tour.services;
//
//import com.california.propertymanagement.dto.DeviceInfo;
//import com.california.propertymanagement.exception.TokenRefreshException;
//import com.california.propertymanagement.models.RefreshToken;
//import com.california.propertymanagement.models.UserDevice;
//import com.california.propertymanagement.repositories.UserDeviceRepository;
//import com.california.propertymanagement.repositories.UserRepository;
import com.vti.booking_tour.dto.DeviceInfo;
import com.vti.booking_tour.entities.RefreshToken;
import com.vti.booking_tour.entities.UserDevice;
import com.vti.booking_tour.exception.TokenRefreshException;
import com.vti.booking_tour.repositories.UserDeviceRepository;
import com.vti.booking_tour.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeviceService {

	@Autowired
    private UserDeviceRepository userDeviceRepository;


    @Autowired
    UserRepository userRepository;

    public Optional<UserDevice> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setIsRefreshActive(true);
        return userDevice;
    }

    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));

        if (!userDevice.getIsRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }

    public void deleteAll()
    {
        userDeviceRepository.deleteAll();
    }

    public void deleteOldDevice(Long userId)
    {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        userDeviceRepository.dele
    }
}
