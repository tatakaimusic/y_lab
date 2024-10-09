package org.example.repository;

import org.example.model.User;

public interface UserMemoryRepository {

    User create(User user);

    User update(User user, String email);

    void delete(User user);

    User get(String email);

}
