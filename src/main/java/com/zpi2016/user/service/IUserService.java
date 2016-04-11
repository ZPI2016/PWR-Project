package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.support.common.GenericService;
import com.zpi2016.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by aman on 11.04.16.
 */
@Service
public interface IUserService extends GenericService<User> {

    public Location findAddress(UUID id);

    public Location updateAddress(Location newAddress, UUID id);

    public User findByEmail(final String email);

}
