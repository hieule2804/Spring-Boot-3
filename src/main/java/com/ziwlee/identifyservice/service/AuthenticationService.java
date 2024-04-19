package com.ziwlee.identifyservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ziwlee.identifyservice.dto.request.AuthenticationRequest;
import com.ziwlee.identifyservice.dto.request.IntrospectRequest;
import com.ziwlee.identifyservice.dto.response.AuthenticationResponse;
import com.ziwlee.identifyservice.dto.response.IntrospectRespoense;
import com.ziwlee.identifyservice.exception.AppException;
import com.ziwlee.identifyservice.exception.ErrorCode;
import com.ziwlee.identifyservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.singerKey}")
    protected String SINGER_KEY ;

    //verify token
    public IntrospectRespoense introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
return IntrospectRespoense.builder()
        .valid(verified && expityTime.after(new Date()))
        .build();
    }
   public AuthenticationResponse authenticate (AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(request.getPassword(),
                user.getPassword());
        if(!authenticated)
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        //generate a token

       var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(String username) {
      //create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
      //create payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("ziwlee")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim","custom")
                .build();
  Payload payload = new Payload(jwtClaimsSet.toJSONObject());

  JWSObject jwsObject = new JWSObject(header,payload);

  //sign token
        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot create token",e);
            throw new RuntimeException(e);
        }
    }

}
