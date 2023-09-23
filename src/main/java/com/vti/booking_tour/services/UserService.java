package com.vti.booking_tour.services;
//import com.california.propertymanagement.dto.LogOutAllDeviceRequest;
//import com.california.propertymanagement.event.OnUserLogoutAllSuccessEvent;
//import com.california.propertymanagement.exception.UserLogoutException;
//import com.california.propertymanagement.models.User;
//import com.california.propertymanagement.models.UserDevice;
//import com.california.propertymanagement.payload.request.UpdateProfile;
//import com.california.propertymanagement.payload.request.UpdateSocial;
//import com.california.propertymanagement.payload.response.UserResponse;
//import com.california.propertymanagement.repositories.UserRepository;
import com.vti.booking_tour.dto.LogOutAllDeviceRequest;
import com.vti.booking_tour.entities.User;
import com.vti.booking_tour.entities.UserDevice;
import com.vti.booking_tour.event.OnUserLogoutAllSuccessEvent;
import com.vti.booking_tour.exception.UserLogoutException;
import com.vti.booking_tour.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class UserService {
    @Autowired
    UserRepository userRepository;
//    public User updateProfile(UpdateProfile updateProfile){
//        Optional<User> userOptional = userRepository.findById(updateProfile.getUserID());
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            user.setEmail(updateProfile.getEmail());
//           // user.setUserName(updateProfile.getUsername());
//            user.setAvatar(updateProfile.getUrl_avatar());
//            user.setFirstName(updateProfile.getFirstName());
//            user.setLastName(updateProfile.getLastName());
//            user.setDesc(updateProfile.getDesc());
//            user.setPhoneNumber(updateProfile.getPhone());
//            user.setAddress(updateProfile.getAddress());
//            return userRepository.save(user);
//        }
//        return null;
//    }

//    public User updateSocial(UpdateSocial updateSocial){
//        Optional<User> userOptional = userRepository.findById(updateSocial.getUserID());
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            user.setFacebook(updateSocial.getFacebook());
//            user.setInstagram(updateSocial.getInstagram());
//            user.setSkype(updateSocial.getSkype());
//            user.setWebsite(updateSocial.getWebsite());
//            user.setTwitter(updateSocial.getTwitter());
//            user.setYoutube(updateSocial.getYoutube());
//            return userRepository.save(user);
//        }
//        return null;
//    }
//
//    @Autowired
//    DepartmentRepository departmentRepository;
//    public List<User> getUserPagging(long parentID, int pageNum, int pageSize)
//    {
//        Pageable pageOne = PageRequest.of(pageNum,pageSize);
//        Page<User> studentPagging = userRepository.findAllByParentID(parentID,pageOne);
//        return studentPagging.stream().toList();
//    }

//    public void removeUser(long userID){
//        Optional<User> optionalUser = userRepository.findById(userID);
//        if(optionalUser.isPresent()){
//            User user = optionalUser.get();
//            user.setActive(false);
//            userRepository.save(user);
//        }
//    }

//    public List<UserResponse> getAllUser()
//    {
//         List<User> userList =  findAllByIsActive();
//                 //userRepository.findAll();
//         int index = 0;
//         try{
//             for(User user: userList){
//                 if(user.getParentID()==0){
//                     try{
//                         userList.remove(index);
//                     }catch (Exception ex){
//                     }
//                 }
//                 index ++;
//             }
//         }catch (Exception exception){
//         }
//        List<UserResponse> userResponseList = new ArrayList<>();
//        for(User user1: userList)
//        {
//            UserResponse userResponse = new UserResponse();
//
//            userResponse.setNote(user1.getNote());
//            userResponse.setId(user1.getId());
//            userResponse.setParentID(user1.getParentID());
//            userResponse.setActive(user1.isActive());
//            userResponse.setUserName(user1.getUserName());
//            userResponse.setCreatedTime(user1.getCreatedTime());
//            userResponse.setFullName(user1.getFullName());
//            userResponse.setRoles(user1.getRoles());
//         //   userResponse.setDepartmentID(user1.getDepartmentID());
//
////            Optional<Department> departmentOptional = departmentRepository.findById(user1.getDepartmentID());
////            userResponse.setDepartmentName(departmentOptional.get().getDepartmentName());
//
//
//            userResponseList.add(userResponse);
//        }
//
//        return  userResponseList;
//    }
    public User findUserByUserName(String userName){
        List<User> userList = userRepository.findAll();
        for(User user: userList){
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return  null;
    }
    public List<User> findAllByIsActive()
    {
        List<User> allUser = userRepository.findAll();
        List<User> returnList = new ArrayList<>();
        for(User user: allUser)
        {
            if(user.isActive())
            {
                returnList.add(user);
            }
        }
        return  returnList;
    }
    public User findUserById(long id){
        return  userRepository.findById(id).get();
    }
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public void logout(String deviceId , long userId,
                      LogOutAllDeviceRequest logOutRequest){
        UserDevice userDevice = userDeviceService.findByUserId(userId)
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(deviceId, "Invalid device Id supplied. No matching device found for the given user "));
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
        User currentUser = findUserById(userId);
        OnUserLogoutAllSuccessEvent logoutSuccessEvent = new OnUserLogoutAllSuccessEvent
                (currentUser.getUsername(),
                 logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
    }
}
