package com.zpi2016.service.user;

import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import com.zpi2016.repository.UserRepository;
import com.zpi2016.utils.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by aman on 13.03.16.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        repository = userRepository;
    }

    @Override
    @Transactional
    public User save(final User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if(existing != null){
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username = %s or mail = %s",
                    user.getUsername(), user.getEmail()));
        }
        user.setAddress(new Location(1f,1f));
        user.setDob(new Date());
        return repository.save(user);
    }

    @Override
    public User findOne(int ID) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public void delete(final User entity) {
    }

    @Override
    public boolean exists(int ID) {
        return false;
    }

}
