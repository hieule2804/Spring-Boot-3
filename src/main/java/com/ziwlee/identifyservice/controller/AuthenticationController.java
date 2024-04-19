package com.ziwlee.identifyservice.controller;

import com.nimbusds.jose.JOSEException;
import com.ziwlee.identifyservice.dto.request.ApiResponse;
import com.ziwlee.identifyservice.dto.request.AuthenticationRequest;
import com.ziwlee.identifyservice.dto.request.IntrospectRequest;
import com.ziwlee.identifyservice.dto.response.AuthenticationResponse;
import com.ziwlee.identifyservice.dto.response.IntrospectRespoense;
import com.ziwlee.identifyservice.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
 @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
    var result = authenticationService.authenticate(request);
 return ApiResponse.<AuthenticationResponse>builder()
         .result(result)
         .build();
 }

    @PostMapping("/introspect")
    ApiResponse<IntrospectRespoense> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectRespoense>builder()
                .result(result)
                .build();
    }
}
