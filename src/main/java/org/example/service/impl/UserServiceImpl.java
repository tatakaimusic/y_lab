package org.example.service.impl;

import org.example.model.User;
import org.example.repository.impl.UserMemoryRepositoryImpl;
import org.example.service.UserService;

import java.util.Objects;

public class UserServiceImpl implements UserService {

    private final UserMemoryRepositoryImpl userMemoryRepository;

    public UserServiceImpl(UserMemoryRepositoryImpl userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    public User create(User user) {
        Objects.requireNonNull(user, "User must not be null");
        if (get(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return userMemoryRepository.create(user);
    }

    public User update(User user, String oldEmail) {
        Objects.requireNonNull(user, "User must not be null");
        return userMemoryRepository.update(user, oldEmail);
    }

    public void delete(User user) {
        Objects.requireNonNull(user, "User must not be null");
        userMemoryRepository.delete(user);
    }

    public User get(String email) {
        User user = userMemoryRepository.get(email);
        if (user == null) {
            throw new IllegalArgumentException("User with this email does not exist");
        }
        return user;
    }

}
