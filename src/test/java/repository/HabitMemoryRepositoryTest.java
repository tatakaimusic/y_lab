package repository;

import org.example.model.Habit;
import org.example.model.HabitPeriod;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HabitMemoryRepositoryTest {

    HabitMemoryRepositoryImpl habitMemoryRepository = new HabitMemoryRepositoryImpl();

    @BeforeEach
    void init() {
        habitMemoryRepository.clear();
        habitMemoryRepository.create(100L, new Habit("title", "description", HabitPeriod.DAILY));
    }

    @Test
    void create() {
        habitMemoryRepository.create(100L, new Habit("new title", "description", HabitPeriod.DAILY));
        Assertions.assertTrue(habitMemoryRepository.get(100L, "new title").isPresent());
    }

    @Test
    void get() {
        Assertions.assertTrue(habitMemoryRepository.get(100L, "title").isPresent());
    }

    @Test
    void getAllHabitsByUserId() {
        habitMemoryRepository.create(100L, new Habit("title2", "description", HabitPeriod.DAILY));
        Assertions.assertEquals(2, habitMemoryRepository.getAllHabitsByUserId(100L).size());
    }

    @Test
    void getAllHabits() {
        habitMemoryRepository.create(1001L, new Habit("title2", "description", HabitPeriod.DAILY));
        Assertions.assertEquals(2, habitMemoryRepository.getAllHabits().size());
    }

    @Test
    void update() {
        Habit habit = habitMemoryRepository.get(100L, "title").orElse(null);
        String oldTitle = habit.getTitle();
        habit.setTitle("new title");
        habitMemoryRepository.update(100L, oldTitle, habit);
        Assertions.assertTrue(habitMemoryRepository.get(100L, "new title").isPresent());
    }

    @Test
    void delete() {
        habitMemoryRepository.delete(100L, "title");
        Assertions.assertFalse(habitMemoryRepository.get(100L, "title").isPresent());
    }

}
