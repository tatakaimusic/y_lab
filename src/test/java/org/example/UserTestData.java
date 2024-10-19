package org.example;

import org.example.model.User;

public class UserTestData {

    public static final String USER_NAME = "username";
    public static final String USER_MAIL = "new@mail.ru";
    public static final String USER_PASSWORD = "password";

    public static final User NEW_USER = new User(USER_NAME, USER_MAIL, USER_PASSWORD);

    public static final String USER_EXISTED_EMAIL = "user@mail.ru";
    public static final Long USER_EXISTED_ID = 100L;

    public static final User EXISTED_USER = new User(USER_EXISTED_ID, USER_NAME, USER_EXISTED_EMAIL, USER_PASSWORD);

    public static final String UPDATED_EMAIL = "updated@mail.ru";

}
