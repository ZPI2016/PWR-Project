package com.zpi2016.utils;

/**
 * Created by aman on 13.03.16.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(){
        super();
    }

    public UserAlreadyExistsException(String msg){
        super(msg);
    }

}
