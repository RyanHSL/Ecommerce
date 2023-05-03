package com.mrui22.ecommerce.services;

import com.mrui22.ecommerce.config.Alerts;
import com.mrui22.ecommerce.exceptions.AuthenticationFailException;
import com.mrui22.ecommerce.model.AuthenticationToken;
import com.mrui22.ecommerce.model.User;
import com.mrui22.ecommerce.repository.TokenRepository;
import com.mrui22.ecommerce.utils.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServices {

    @Autowired
    TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (HelperMethods.notNull(authenticationToken)) {
            if (HelperMethods.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!HelperMethods.notNull(token)) {
            throw new AuthenticationFailException(Alerts.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!HelperMethods.notNull(getUser(token))) {
            throw new AuthenticationFailException(Alerts.AUTH_TOEKN_NOT_VALID);
        }
    }
}
