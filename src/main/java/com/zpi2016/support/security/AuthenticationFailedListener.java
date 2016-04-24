package com.zpi2016.support.security;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Created by aman on 24.04.16.
 */
@Component
public class AuthenticationFailedListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final Logger LOG = Logger.getLogger(AuthenticationFailedListener.class);

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object userName = event.getAuthentication().getPrincipal();
        Object credentials = event.getAuthentication().getCredentials();
        System.err.println("Failed login using USERNAME [" + userName + "]");
        System.err.println("Failed login using PASSWORD [" + credentials + "]");
    }
}