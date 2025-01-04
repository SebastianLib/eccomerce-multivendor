package com.zosh.service;

import com.zosh.model.User;
import com.zosh.request.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest req);

    public User findUserByJwtToken(String jwt) throws Exception;

    void checkIfAdmin(String jwt) throws Exception;
}
