package com.zosh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zosh.config.JwtProvider;
import com.zosh.domain.USER_ROLE;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.SignupRequest;
import com.zosh.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String createUser(@org.jetbrains.annotations.NotNull SignupRequest req) {

        User user = userRepository.findByEmail(req.getEmail());

        if(user==null){
           User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("435435433");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
      String email = jwtProvider.getEmailFromJwtToken(jwt);

      User user = userRepository.findByEmail(email);
      if(user == null){
          throw new Exception("User not found");
      }
        return user;
    }

    public void checkIfAdmin(String jwt) throws Exception {
        User user = findUserByJwtToken(jwt);

        if (user.getRole() != USER_ROLE.ROLE_CUSTOMER) {
            throw new RuntimeException("Access denied: User is not an admin");
        }
    }
}
