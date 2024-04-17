package com.ziwlee.identifyservice.controller;

import com.ziwlee.identifyservice.dto.request.ApiResponse;
import com.ziwlee.identifyservice.dto.request.UserCreationRequest;
import com.ziwlee.identifyservice.dto.request.UserUpdateRequest;
import com.ziwlee.identifyservice.entity.User;
import com.ziwlee.identifyservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
       return apiResponse;
    }

    @GetMapping()
    List<User> getListUser(){
return userService.listUser();
    }
    @GetMapping("/{userId}")
    User getUser(@PathVariable String userId){
  return userService.getUser(userId);
    }
     @PutMapping("/{userId}")
    User updateUser(@RequestBody UserUpdateRequest request ,@PathVariable String userId){
return userService.updateUser(userId,request);
     }
     @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "Delete Successful";
     }
}
