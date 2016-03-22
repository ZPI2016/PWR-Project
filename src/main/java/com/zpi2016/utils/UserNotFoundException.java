package com.zpi2016.utils;

/**
 * Created by filip on 22.03.2016.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg){
        super(msg);
    }
}