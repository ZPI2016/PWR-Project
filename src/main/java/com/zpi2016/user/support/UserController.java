package com.zpi2016.user.support;

import com.zpi2016.model.Location;
import com.zpi2016.user.User;
import com.zpi2016.service.user.UserService;
import com.zpi2016.utils.UserAlreadyExistsException;
import com.zpi2016.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody @Valid final User user) {
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserWithId(@PathVariable UUID id) {
        return userService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User updateUserWithId(@RequestBody @Valid final User user, @PathVariable UUID id) {
        return userService.update(user, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserWithId(@PathVariable UUID id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/{id}/address", method = RequestMethod.GET)
    public Location getAddressOfUserWithId(@PathVariable UUID id) {
        return userService.findAddress(id);
    }

    @RequestMapping(value = "/{id}/address", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Location updateAddressOfUserWithId(@RequestBody @Valid final Location address, @PathVariable UUID id) {
        return userService.updateAddress(address, id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

}