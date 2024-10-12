package org.example.repository;

import org.example.model.User;

import java.util.Optional;

public interface UserMemoryRepository {

    User create(User user);

    User update(User user, String email);

    void delete(User user);

    Optional<User> get(String email);

}
