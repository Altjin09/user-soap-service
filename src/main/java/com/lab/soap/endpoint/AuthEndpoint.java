package com.lab.soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.lab.soap.service.AuthService;
import com.lab.soap.ws.LoginUserRequest;
import com.lab.soap.ws.LoginUserResponse;
import com.lab.soap.ws.RegisterUserRequest;
import com.lab.soap.ws.RegisterUserResponse;
import com.lab.soap.ws.ValidateTokenRequest;
import com.lab.soap.ws.ValidateTokenResponse;

@Endpoint
public class AuthEndpoint {

    private static final String NAMESPACE_URI = "http://lab.com/soap";

    @Autowired
    private AuthService authService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();

        String result = authService.register(request.getUsername(), request.getPassword());
        response.setMessage(result);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginUserRequest")
    @ResponsePayload
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) {
        LoginUserResponse response = new LoginUserResponse();

        String token = authService.login(request.getUsername(), request.getPassword());

        if (token == null) {
            response.setToken("");
            response.setMessage("Login failed");
        } else {
            response.setToken(token);
            response.setMessage("Login success");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "validateTokenRequest")
    @ResponsePayload
    public ValidateTokenResponse validateToken(@RequestPayload ValidateTokenRequest request) {
        ValidateTokenResponse response = new ValidateTokenResponse();

        boolean valid = authService.validate(request.getToken());
        response.setValid(valid);

        if (valid) {
            response.setUserId(authService.getUserIdFromToken(request.getToken()));
            response.setUsername(authService.getUsernameFromToken(request.getToken()));
        } else {
            response.setUserId(0L);
            response.setUsername("");
        }

        return response;
    }
}