package org.example.service;

import org.example.model.User;

public interface UserService {

    User create(User user);

    User update(User user, String oldEmail);

    void delete(User user);

    User get(String email);

}
