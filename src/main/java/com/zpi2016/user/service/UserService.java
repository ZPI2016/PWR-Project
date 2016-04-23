package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.RoleRepository;
import com.zpi2016.user.repository.UserRepository;
import com.zpi2016.user.domain.Role;
import com.zpi2016.user.support.CustomUserDetails;
import com.zpi2016.user.support.UserAlreadyExistsException;
import com.zpi2016.user.support.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User save(final User user) {
        checkUniqueConstraints(user);
        user.setDob(new Date());
        user.setAddress(new Location(1f,1f));
        User saved =  userRepository.save(user);
        Role role = new Role(saved, "USER");
        roleRepository.save(role);
        return saved;
    }

    @Override
    public User findOne(final UUID id) {
        return userRepository.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
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
        userRepository.delete(id);
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
        if (!userRepository.exists(id)) {
            throw new UserNotFoundException(String.format("Could not find user with id: %s", id));
        }
    }

    private void checkUniqueConstraints(User user) {
        User existing = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
        }
    }

    private void checkUniqueConstraints(User user, UUID id) {
        User existing = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existing != null && !existing.getId().equals(id)) {
            throw new UserAlreadyExistsException(String.format(
                    "There already exists a user with username: %s or email: %s",
                    user.getUsername(), user.getEmail()));
        }
    }

    // security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("No user present with username: "+username);
        }
        else{
            List<String> roles = roleRepository.findRoleByUsername(username);
            return new CustomUserDetails(user, roles);
        }
    }
}
