package com.vti.booking_tour.controllers;

import com.vti.booking_tour.dto.LoginForm;
import com.vti.booking_tour.entities.RefreshToken;
import com.vti.booking_tour.entities.User;
import com.vti.booking_tour.entities.UserDevice;
import com.vti.booking_tour.models.ResponseObject;
import com.vti.booking_tour.payload.response.JwtResponse2;
import com.vti.booking_tour.repositories.RoleRepository;
import com.vti.booking_tour.repositories.UserRepository;
import com.vti.booking_tour.security.jwt.JwtProvider;
import com.vti.booking_tour.security.jwt.JwtUtils;
import com.vti.booking_tour.services.RefreshTokenService;
import com.vti.booking_tour.services.UserDeviceService;
import com.vti.booking_tour.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired(required=true)
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        Optional<User> optionalUser = userRepository.findByUsername(loginForm.getUsername());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(202, "Fail", "User or password is incorrect!!"));
        }
        User user = optionalUser.get();
        if(!user.getPassword().equals(encoder.encode(loginForm.getPassword()))){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(202, "Fail", "User or password is incorrect!!"));
        }
        if (user.isActive()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtProvider.generateJwtToken(authentication);
            userDeviceService.findByUserId(user.getId())
                    .map(UserDevice::getRefreshToken)
                    .map(RefreshToken::getId)
                    .ifPresent(refreshTokenService::deleteById);

            Optional<UserDevice> optionalUserDevice = userDeviceService.findByUserId(user.getId());
            if(optionalUserDevice.isPresent()){
                userDeviceService.deleteOldDevice(user.getId());
            }
            UserDevice userDevice = userDeviceService.createUserDevice(loginForm.getDeviceInfo());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken();
            userDevice.setUser(user);
            userDevice.setRefreshToken(refreshToken);
            refreshToken.setUserDevice(userDevice);
            refreshToken = refreshTokenService.save(refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Ok",
                            new JwtResponse2(jwtToken,
                                    refreshToken.getToken(),
                                    jwtProvider.getExpiryDuration(),
                                    user.getId(), user.getRoles(), loginForm.getUsername(),
                                    user.getEmail())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(400, "Fail", "User has been deactivated/locked !!"));
    }
}
