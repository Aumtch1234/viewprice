package com.ViewPrice.controller;

import com.ViewPrice.service.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ViewPrice.config.JwtProvider;
import com.ViewPrice.modal.User;
import com.ViewPrice.repository.UserRepository;
import com.ViewPrice.response.AuthReponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthReponse> register(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            throw new Exception("email is already used with another account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullname(user.getFullname());

        User savedUser = userRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthReponse res = new AuthReponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");
        


        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    //login
    @PostMapping("/signin")
    public ResponseEntity<AuthReponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthReponse res = new AuthReponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login success");



        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }
}
