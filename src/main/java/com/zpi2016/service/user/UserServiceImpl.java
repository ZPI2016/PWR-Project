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

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User save(final User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username = %s or mail = %s",
                    user.getUsername(), user.getEmail()));
        }
        return repository.save(user);
    }

    @Override
    public User findOne(final Integer id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User update(User user, Integer id) {
        if (exists(id)) {
            User existing = findOne(id);
            existing.copy(user);
            return existing;
        } else {
            return save(user);
        }
    }

    @Override
    @Transactional
    public void delete(final User user) {
        repository.delete(user);
    }

    @Override
    public boolean exists(final Integer id) {
        return repository.exists(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

}
