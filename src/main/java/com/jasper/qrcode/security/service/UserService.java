package com.jasper.qrcode.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jasper.qrcode.security.SecurityUtils;
import com.jasper.qrcode.security.model.User;
import com.jasper.qrcode.security.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }
   
   public Optional<User> getUserWithUsername(String username) {
	return userRepository.findOneByUsername(username);
   }

   public User saveUser(User user) {
	   return userRepository.saveAndFlush(user);
   }
}
