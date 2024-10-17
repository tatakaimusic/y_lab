package repository;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class HabitHistoryMemoryRepositoryTest {

    HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository = new HabitHistoryMemoryRepositoryImpl();
    HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();

    @BeforeEach
    void init() {
        habitMemoryRepository.clear();
        habitHistoryMemoryRepository.clear();
        Habit habit = new Habit("title", "description", HabitPeriod.DAILY);
        habit = habitMemoryRepository.create(100L, habit, LocalDate.now().minusDays(3));
        habitHistoryMemoryRepository.create(habit.getId(), LocalDate.now().minusDays(2));
        habitHistoryMemoryRepository.create(habit.getId(), LocalDate.now().minusDays(1));
        habitHistoryMemoryRepository.create(habit.getId(), LocalDate.now().minusDays(0));
    }

    @Test
    @DisplayName("Тест отметки привычки")
    void mark() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        habitHistoryMemoryRepository.mark(habit.getId(), LocalDate.now());
        Assertions.assertTrue(habitHistoryMemoryRepository.getLocalDateMark(habit.getId(), LocalDate.now()));
    }

    @Test
    @DisplayName("Тест создания даты в истории привычки")
    void create() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        habitHistoryMemoryRepository.create(habit.getId(), LocalDate.now().plusDays(1));
        Assertions.assertNotNull(habitHistoryMemoryRepository.getLocalDateMark(habit.getId(), LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Тест удаления истории привычки")
    void delete() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        habitHistoryMemoryRepository.delete(habit.getId());
        Assertions.assertNull(habitHistoryMemoryRepository.getHabitHistory(habit.getId()));
    }

    @Test
    @DisplayName("Тест получения истории привычки")
    void getHabitHistory() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        Assertions.assertEquals(3, habitHistoryMemoryRepository.getHabitHistory(habit.getId()).size());
    }

    @Test
    @DisplayName("Тест получения статуса выполненности привычки")
    void getLocalDateMark() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        Assertions.assertFalse(habitHistoryMemoryRepository.getLocalDateMark(habit.getId(), LocalDate.now()));
    }

}
