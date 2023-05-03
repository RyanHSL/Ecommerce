package com.mrui22.ecommerce.controller;

import com.mrui22.ecommerce.dto.ResponseDTO;
import com.mrui22.ecommerce.dto.user.SignInDTO;
import com.mrui22.ecommerce.dto.user.SignInResponseDTO;
import com.mrui22.ecommerce.dto.user.SignUpDTO;
import com.mrui22.ecommerce.exceptions.AuthenticationFailException;
import com.mrui22.ecommerce.model.User;
import com.mrui22.ecommerce.repository.UserRepository;
import com.mrui22.ecommerce.services.AuthenticationServices;
import com.mrui22.ecommerce.services.UserServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    AuthenticationServices authenticationServices;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationServices.authenticate(token);
        return userRepo.findAll();
    }

    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignUpDTO signupDTO) {
        return userServices.signup(signupDTO);
    }

    @PostMapping("/signin")
    public SignInResponseDTO signIn(@RequestBody SignInDTO signInDTO) {
        return userServices.signIn(signInDTO);
    }
}
