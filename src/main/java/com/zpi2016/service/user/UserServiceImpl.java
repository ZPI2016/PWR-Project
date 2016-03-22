package com.zpi2016.service.user;

import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import com.zpi2016.repository.UserRepository;
import com.zpi2016.utils.UserAlreadyExistsException;
import com.zpi2016.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User findOne(final String id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User update(User user, String id) {
        checkIfUserExists(id);
        checkUniqueConstraints(user);
        User existing = findOne(id);
        existing.copy(user);
        return existing;
    }

    @Override
    public void delete(final User user) {
        repository.delete(user);
    }

    @Override
    @Transactional
    public void delete(String id) {
        checkIfUserExists(id);
        delete(findOne(id));
    }

    @Override
    public boolean exists(final String id) {
        return repository.exists(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public Location findAddress(String id) {
        checkIfUserExists(id);
        return findOne(id).getAddress();
    }

    @Override
    @Transactional
    public Location updateAddress(Location newAddress, String id) {
        checkIfUserExists(id);
        Location currentAddress = findOne(id).getAddress();
        currentAddress.copy(newAddress);
        return currentAddress;
    }

    private void checkUniqueConstraints(User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
        }
    }

    private void checkIfUserExists(String id) {
        if (!exists(id)) {
            throw new UserNotFoundException(String.format("Could not find user with id: %s", id));
        }
    }
}
