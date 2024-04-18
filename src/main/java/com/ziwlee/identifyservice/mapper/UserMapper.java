package com.ziwlee.identifyservice.mapper;

import com.ziwlee.identifyservice.dto.request.UserCreationRequest;
import com.ziwlee.identifyservice.dto.request.UserUpdateRequest;
import com.ziwlee.identifyservice.dto.response.UserResponse;
import com.ziwlee.identifyservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
//@Mapping(source = "",target = "") // used when have field difference map from source to target
//@Mapping(target = "" , ignore = "true") //used when don't wanna map field( in target)
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user , UserUpdateRequest request);
}
