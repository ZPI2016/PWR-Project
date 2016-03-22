package com.zpi2016.service.user;

import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import com.zpi2016.service.GenericService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by filip on 22.03.2016.
 */
public interface UserService extends GenericService<User> {

    Location findAddress(String id);

    Location updateAddress(Location newAddress, String id);
}
