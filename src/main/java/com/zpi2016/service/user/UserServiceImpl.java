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
        checkUniqueConstraints(user);
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
        checkUniqueConstraints(user);
        if (exists(id)) {
            User existing = findOne(id);
            existing.copy(user);
            return existing;
        } else {
            return null;
        }
    }

    @Override
    public void delete(final User user) {
        repository.delete(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (exists(id)) {
            delete(findOne(id));
        }
    }

    @Override
    public boolean exists(final Integer id) {
        return repository.exists(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }


    @Override
    @Transactional
    public Location findAddress(Integer id) {
        if (exists(id)) {
            return findOne(id).getAddress();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Location updateAddress(Location newAddress, Integer id) {
        if (exists(id)) {
            Location currentAddress = findOne(id).getAddress();
            currentAddress.copy(newAddress);
            return currentAddress;
        } else {
            return null;
        }
    }

    private void checkUniqueConstraints(User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username = %s or mail = %s",
                    user.getUsername(), user.getEmail()));
        }
    }
}
