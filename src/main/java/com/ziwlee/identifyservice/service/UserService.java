package com.ziwlee.identifyservice.service;

import com.ziwlee.identifyservice.dto.request.UserCreationRequest;
import com.ziwlee.identifyservice.dto.request.UserUpdateRequest;
import com.ziwlee.identifyservice.dto.response.UserResponse;
import com.ziwlee.identifyservice.entity.User;
import com.ziwlee.identifyservice.exception.AppException;
import com.ziwlee.identifyservice.exception.ErrorCode;
import com.ziwlee.identifyservice.mapper.UserMapper;
import com.ziwlee.identifyservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    public User createUser(UserCreationRequest request){


if(userRepository.existsByUsername(request.getUsername()))
    throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
       return userRepository.save(user);
    }


    public List<User> listUser(){
        return userRepository.findAll();
    }

    public UserResponse getUser(String id){
        return  userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId , UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found"));
       userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser(String userId){

        userRepository.deleteById(userId);
    }
}
