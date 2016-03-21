package com.zpi2016.controller;

import com.zpi2016.model.User;
import com.zpi2016.service.user.UserService;
import com.zpi2016.utils.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

/**
 * Created by aman on 13.03.16.
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody @Valid final User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getMessage();
    }

}