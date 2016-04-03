package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.support.common.GenericService;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import com.zpi2016.user.support.UserAlreadyExistsException;
import com.zpi2016.user.support.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
@Service
public class UserService implements GenericService<User> {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User save(final User user) {
        checkUniqueConstraints(user);
        return repository.save(user);
    }

    @Override
    public User findOne(final UUID id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User update(User user, UUID id) {
        checkIfUserExists(id);
        checkUniqueConstraints(user, id);
        User existing = findOne(id);
        existing.copy(user);
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        checkIfUserExists(id);
        repository.delete(id);
    }

    @Transactional
    public Location findAddress(UUID id) {
        checkIfUserExists(id);
        return findOne(id).getAddress();
    }

    @Transactional
    public Location updateAddress(Location newAddress, UUID id) {
        checkIfUserExists(id);
        Location currentAddress = findOne(id).getAddress();
        currentAddress.copy(newAddress);
        return currentAddress;
    }

    private void checkIfUserExists(UUID id) {
        if (!repository.exists(id)) {
            throw new UserNotFoundException(String.format("Could not find user with id: %s", id));
        }
    }

    private void checkUniqueConstraints(User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
        }
    }

    private void checkUniqueConstraints(User user, UUID id) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null && !existing.getId().equals(id)) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
        }
    }
}
