package com.demo.simpleChat.controllers.rest;

import static com.chat.App.REST_SERVICE_PREFIX;

import com.chat.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = REST_SERVICE_PREFIX + "auth", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean authenticateUser(@RequestBody String userName) {
        return authService.loginUser(userName);
    }
}
