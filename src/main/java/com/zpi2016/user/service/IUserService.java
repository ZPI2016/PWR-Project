package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.support.common.GenericService;
import com.zpi2016.user.domain.User;

import java.util.UUID;

/**
 * Created by aman on 23.04.16.
 */
public interface IUserService extends GenericService<User> {

    Location findAddress(UUID id);

    Location updateAddress(Location address, UUID id);

}
