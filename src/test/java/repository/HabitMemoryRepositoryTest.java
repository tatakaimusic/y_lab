package repository;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.repository.impl.memory.HabitMemoryRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HabitMemoryRepositoryTest {

    HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();

    @BeforeEach
    void init() {
        habitMemoryRepository.clear();
        habitMemoryRepository.create(100L, new Habit("title", "description", HabitPeriod.DAILY));
    }

    @Test
    @DisplayName("Тест создания привычки")
    void create() {
        habitMemoryRepository.create(100L, new Habit("new title", "description", HabitPeriod.DAILY));
        Assertions.assertTrue(habitMemoryRepository.get(100L, "new title").isPresent());
    }

    @Test
    @DisplayName("Тест получения привычки")
    void get() {
        Assertions.assertTrue(habitMemoryRepository.get(100L, "title").isPresent());
    }

    @Test
    @DisplayName("Тест получения привычек по userId")
    void getAllHabitsByUserId() {
        habitMemoryRepository.create(100L, new Habit("title2", "description", HabitPeriod.DAILY));
        Assertions.assertEquals(2, habitMemoryRepository.getAllHabitsByUserId(100L).size());
    }

    @Test
    @DisplayName("Тест получения всех привычек")
    void getAllHabits() {
        habitMemoryRepository.create(1001L, new Habit("title2", "description", HabitPeriod.DAILY));
        Assertions.assertEquals(2, habitMemoryRepository.getAllHabits().size());
    }

    @Test
    @DisplayName("Тест обновления привычки")
    void update() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        String oldTitle = habit.getTitle();
        habit.setTitle("new title");
        habitMemoryRepository.update(100L, oldTitle, habit);
        Assertions.assertTrue(habitMemoryRepository.get(100L, "new title").isPresent());
    }

    @Test
    @DisplayName("Тест удаления привычки")
    void delete() {
        habitMemoryRepository.delete(100L, "title");
        Assertions.assertFalse(habitMemoryRepository.get(100L, "title").isPresent());
    }

}
