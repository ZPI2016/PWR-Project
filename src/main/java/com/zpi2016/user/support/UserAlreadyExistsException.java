package com.zpi2016.user.support;

/**
 * Created by aman on 13.03.16.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String msg){
        super(msg);
    }

}
