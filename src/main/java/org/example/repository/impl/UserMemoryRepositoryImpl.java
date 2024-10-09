package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.UserMemoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserMemoryRepositoryImpl implements UserMemoryRepository {

    private static Long counter = 100L;

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        Objects.requireNonNull(user, "User must not be null");
        user.setId(counter++);
        users.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User update(User user, String oldEmail) {
        users.remove(oldEmail);
        users.put(user.getEmail(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        users.remove(user.getEmail());
    }

    @Override
    public User get(String email) {
        return users.get(email);
    }

}
