package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import com.zpi2016.core.common.service.GenericService;
import com.zpi2016.user.support.UserAlreadyExistsException;
import com.zpi2016.user.support.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
@Service
public class UserService implements GenericService<User>, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Value("${salt}")
    private String salt;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(String.format(
                "There is no user with username: %s", username));
        return user;
    }

    @Override
    @Transactional
    public User save(final User user) {
        checkUniqueConstraints(user);
        encryptPassword(user);
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
        checkIfAuthorized(id);
        checkIfUserExists(id);
        checkUniqueConstraints(user, id);
        encryptPassword(user);
        User existing = findOne(id);
        existing.updateWithPropertiesFrom(user);
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        checkIfAuthorized(id);
        checkIfUserExists(id);
        repository.delete(id);
    }

    @Transactional
    public Location findAddress(final UUID id) {
        checkIfUserExists(id);
        return findOne(id).getAddress();
    }

    @Transactional
    public Location updateAddress(final Location newAddress, final UUID id) {
        checkIfAuthorized(id);
        checkIfUserExists(id);
        Location currentAddress = findOne(id).getAddress();
        currentAddress.updateWithPropertiesFrom(newAddress);
        return currentAddress;
    }

    private void encryptPassword(final User user) {

        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void checkIfAuthorized(final UUID id) {
        if (!isAuthorized(id))
            throw new InsufficientAuthenticationException(String.format("You have no rights to manage user with id: %s", id));
    }

    private void checkIfUserExists(final UUID id) {
        if (!repository.exists(id))
            throw new UserNotFoundException(String.format("Could not find user with id: %s", id));
    }

    private void checkUniqueConstraints(final User user) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null)
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
    }

    private void checkUniqueConstraints(final User user, final UUID id) {
        User existing = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null && !existing.getId().equals(id))
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
    }
}
