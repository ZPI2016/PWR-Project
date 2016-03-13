package com.zpi2016.service.user;

import com.zpi2016.model.User;
import com.zpi2016.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public User save(final User enitity) {
        return repository.save(enitity);
    }

    @Override
    public User findOne(int ID) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
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
