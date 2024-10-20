package org.example.service;

import org.example.model.Habit;
import org.example.repository.HabitHistoryRepository;
import org.example.repository.HabitRepository;
import org.example.service.impl.HabitServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.example.HabitTestData.*;
import static org.example.UserTestData.*;
import static org.mockito.Mockito.when;

public class HabitServiceTest {

    private static HabitRepository habitRepository;
    private static HabitHistoryRepository habitHistoryRepository;
    private static HabitService habitService;

    @BeforeAll
    public static void init() {
        habitRepository = Mockito.mock(HabitRepository.class);
        habitHistoryRepository = Mockito.mock(HabitHistoryRepository.class);
        habitService = new HabitServiceImpl(habitRepository, habitHistoryRepository);
    }

    @Test
    @DisplayName("Creating new habit test")
    public void createNewHabit() throws SQLException {
        Habit newHabit = getNewHabit();
        newHabit.setId(HABIT_EXISTED_ID);
        when(habitRepository.get(USER_EXISTED_ID, newHabit.getTitle()))
                .thenReturn(Optional.empty());
        when(habitRepository.create(USER_EXISTED_ID, newHabit, LocalDate.now()))
                .thenReturn(newHabit);
        Habit createdHabit = habitService.create(
                USER_EXISTED_ID, newHabit, LocalDate.now()
        );
        assertThat(createdHabit.getTitle())
                .isEqualTo(newHabit.getTitle());
    }

    @Test
    @DisplayName("Creating habit with existed title test")
    public void createNewHabitWithExistingTitle() throws SQLException {
        Habit existedHabit = getExistedHabit();
        when(habitRepository.get(USER_EXISTED_ID, existedHabit.getTitle()))
                .thenReturn(Optional.of(existedHabit));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> habitService.create(
                                USER_EXISTED_ID, existedHabit, LocalDate.now()
                        )
                );
    }

    @Test
    @DisplayName("Getting existed habit test")
    public void getExistingHabit() throws SQLException {
        Habit existedHabit = getExistedHabit();
        when(habitRepository.get(USER_EXISTED_ID, existedHabit.getTitle()))
                .thenReturn(Optional.of(existedHabit));
        assertThat(habitService.get(USER_EXISTED_ID, existedHabit.getTitle()))
                .isEqualTo(existedHabit);
    }

    @Test
    @DisplayName("Getting not existed habit test")
    public void getNotExistingHabit() throws SQLException {
        Habit newHabit = getNewHabit();
        when(habitRepository.get(USER_EXISTED_ID, newHabit.getTitle()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> habitService.get(
                                USER_EXISTED_ID, newHabit.getTitle()
                        )
                );
    }

    @Test
    @DisplayName("Updating habit title to existed title test")
    public void updateExistingHabitTitle() throws SQLException {
        Habit existedHabit = getExistedHabit();
        String oldTitle = existedHabit.getTitle();
        existedHabit.setTitle(HABIT_UPDATED_TITLE);
        when(habitRepository.get(USER_EXISTED_ID, existedHabit.getTitle()))
                .thenReturn(Optional.of(existedHabit));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> habitService.update(
                                USER_EXISTED_ID, oldTitle, existedHabit
                        )
                );
    }

}
