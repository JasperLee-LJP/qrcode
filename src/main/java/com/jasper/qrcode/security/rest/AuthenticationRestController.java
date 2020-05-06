package com.jasper.qrcode.security.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jasper.qrcode.security.rest.dto.LoginDto;
import com.jasper.qrcode.security.service.UserService;
import com.jasper.qrcode.exception.CommonException;
import com.jasper.qrcode.exception.ErrorCode;
import com.jasper.qrcode.security.UserExistException;
import com.jasper.qrcode.security.jwt.JWTFilter;
import com.jasper.qrcode.security.jwt.TokenProvider;
import com.jasper.qrcode.security.model.Authority;
import com.jasper.qrcode.security.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;
   
   private final UserService userService;
   
   private final PasswordEncoder passwordEncoder;

   public AuthenticationRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
		   UserService userService, PasswordEncoder passwordEncoder) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
      this.userService = userService;
      this.passwordEncoder = passwordEncoder;
   }
   
   @PostMapping("/register")
   public ResponseEntity<User> register(@Valid @RequestBody LoginDto loginDto) {
	   Optional<User> userOptional = userService.getUserWithUsername(loginDto.getUsername());
	   if(!userOptional.isEmpty()) {
		  throw new CommonException(HttpStatus.BAD_REQUEST, ErrorCode.USER_EXIST);
	   }
	   User user = new User();
	   user.setUsername(loginDto.getUsername());
	   user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
	   user.setFirstname("????");
	   user.setLastname("????");
	   user.setEmail("????");
	   user.setActivated(true);
	   Authority authority = new Authority();
	   authority.setName("ROLE_USER");
	   HashSet<Authority> authorities = new HashSet<Authority>();
	   authorities.add(authority);
	   user.setAuthorities(authorities);
	   user = userService.saveUser(user);
	   return new ResponseEntity<User>(user, null, HttpStatus.OK);
   }

   @PostMapping("/authenticate")
   public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDto loginDto) {		

      UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
      String jwt = tokenProvider.createToken(authentication, rememberMe);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
   }

   /**
    * Object to return as body in JWT Authentication.
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}
