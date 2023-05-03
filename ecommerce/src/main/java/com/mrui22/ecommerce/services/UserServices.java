package com.mrui22.ecommerce.services;

import com.mrui22.ecommerce.dto.ResponseDTO;
import com.mrui22.ecommerce.dto.user.SignInDTO;
import com.mrui22.ecommerce.dto.user.SignInResponseDTO;
import com.mrui22.ecommerce.dto.user.SignUpDTO;
import com.mrui22.ecommerce.exceptions.AuthenticationFailException;
import com.mrui22.ecommerce.exceptions.CustomException;
import com.mrui22.ecommerce.model.AuthenticationToken;
import com.mrui22.ecommerce.model.ResponseStatus;
import com.mrui22.ecommerce.model.Role;
import com.mrui22.ecommerce.model.User;
import com.mrui22.ecommerce.repository.UserRepository;
import com.mrui22.ecommerce.utils.HelperMethods;
import javax.transaction.Synchronization;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.mrui22.ecommerce.config.Alerts.USER_CREATED;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthenticationServices authenticationServices;

    Logger logger = LoggerFactory.getLogger(UserServices.class);

    @Transactional
    public ResponseDTO signup(SignUpDTO signupDTO) {
        if (HelperMethods.notNull(userRepo.findByEmail(signupDTO.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // first encrypt the password
        String encryptedPassword = signupDTO.getPassword();
        try {
            encryptedPassword = hashPassword(signupDTO.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }


        User user = new User(signupDTO.getFirstName(), signupDTO.getLastName(), signupDTO.getEmail(), Role.user, encryptedPassword );

        User createdUser;
        try {
            // save the User
            createdUser = userRepo.save(user);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            // save token in database
            authenticationServices.saveConfirmationToken(authenticationToken);
            // success in creating
            return new ResponseDTO(ResponseStatus.success.toString(), USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDTO signIn(SignInDTO signInDTO) {
        User user = userRepo.findByEmail(signInDTO.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User does not exist");
        }
        try{
            if (!user.getPassword().equals(hashPassword(signInDTO.getPassword()))){
                throw new AuthenticationFailException("Wrong password");
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationServices.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("Token is not present");
        }

        return new SignInResponseDTO("success", token.getToken());
    }
}
