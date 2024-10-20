package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.example.UserTestData.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private static UserRepository userRepository;
    private static UserService userService;

    @BeforeAll
    public static void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Creating new user test")
    void createNewUser() throws SQLException {
        User newUser = getNewUser();
        newUser.setId(100L);
        when(userRepository.get(newUser.getEmail()))
                .thenReturn(Optional.empty());
        when(userRepository.create(newUser))
                .thenReturn(Optional.of(newUser));
        User craetedUser = userService.create(newUser);
        assertThat(craetedUser.getEmail())
                .isEqualTo(newUser.getEmail());
    }

    @Test
    @DisplayName("Creating existed user test")
    void createExistedUser() throws SQLException {
        User existedUser = EXISTED_USER;
        when(userRepository.get(existedUser.getEmail()))
                .thenReturn(Optional.of(existedUser));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.create(existedUser));
    }

    @Test
    @DisplayName("Getting existed user test")
    void getExistedUser() throws SQLException {
        when(userRepository.get(EXISTED_USER.getEmail()))
                .thenReturn(Optional.of(EXISTED_USER));
        assertThat(userService.get(EXISTED_USER.getEmail()))
                .isEqualTo(EXISTED_USER);
    }

    @Test
    @DisplayName("Getting not existed user test")
    void getNotExistedUser() throws SQLException {
        when(userRepository.get(NEW_USER.getEmail()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.get(NEW_USER.getEmail()));
    }

}
