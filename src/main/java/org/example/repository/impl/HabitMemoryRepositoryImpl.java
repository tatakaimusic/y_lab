package org.example.repository.impl;

import org.example.model.Habit;
import org.example.repository.HabitMemoryRepository;

import java.time.LocalDate;
import java.util.*;

/**
 * Хранилище привычек пользовтеля.
 */
public class HabitMemoryRepositoryImpl implements HabitMemoryRepository {

    /**
     * Используется для генерации id для првиычки.
     */
    private static Long counter = 100L;

    /**
     * Хранилище привычек, где ключ внешей мапы - userId, ключ внутренней мапы - уникальное название привычки,
     * а значение внутренней мапы - привычка.
     */
    private final Map<Long, Map<String, Habit>> habits = new LinkedHashMap<>();

    /**
     * Создает привычку по userId и экзмпляру Habit.
     * Id назначается с помощью инкрементации counter.
     * @param userId
     * @param habit
     * @return
     */
    @Override
    public Habit create(Long userId, Habit habit) {
        habit.setId(counter++);
        habit.setCreateDate(LocalDate.now());
        if (!habits.containsKey(userId)) {
            habits.put(userId, new HashMap<>());
        }
        habits.get(userId).put(habit.getTitle(), habit);
        return habit;
    }

    /**
     * Создает привычку по userId, экзмпляру Habit и дате.
     * Id назначается с помощью инкрементации counter.
     * @param userId
     * @param habit
     * @return
     */
    @Override
    public Habit create(Long userId, Habit habit, LocalDate createDate) {
        habit.setId(counter++);
        habit.setCreateDate(createDate);
        if (!habits.containsKey(userId)) {
            habits.put(userId, new HashMap<>());
        }
        habits.get(userId).put(habit.getTitle(), habit);
        return habit;
    }

    /**
     * Получение привычки по userId и названию привычки.
     * Вернет пустой Optional, если значения нет.
     * @param userId
     * @param habitTitle
     * @return
     */
    @Override
    public Optional<Habit> get(Long userId, String habitTitle) {
        if (habits.containsKey(userId)) {
            return Optional.ofNullable(habits.get(userId).get(habitTitle));
        }
        return Optional.empty();
    }

    /**
     * Вернет список всех привычек для пользовтеля с userId.
     * Вернет пустой list, если привычек еще нет.
     * @param userId
     * @return
     */
    @Override
    public List<Habit> getAllHabitsByUserId(Long userId) {
        if (habits.containsKey(userId)) {
            return habits.get(userId).values().stream().toList();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Вернет все привычки в хранилизе.
     * Вернет пустой list, если их еще нет.
     * @return
     */
    @Override
    public List<Habit> getAllHabits() {
        List<Habit> result = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Habit>> entry : habits.entrySet()) {
            result.addAll(entry.getValue().values());
        }
        return result;
    }

    /**
     *  Обновит привычку по старому названию.
     * @param userId
     * @param oldHabitTitle
     * @param habit
     */
    @Override
    public void update(Long userId, String oldHabitTitle, Habit habit) {
        if (habits.containsKey(userId)) {
            habits.get(userId).remove(oldHabitTitle);
            habits.get(userId).put(habit.getTitle(), habit);
        }
    }

    /**
     * Удалит привычку по userId и названию привычки.
     * @param userId
     * @param habitTitle
     */
    @Override
    public void delete(Long userId, String habitTitle) {
        if (habits.containsKey(userId)) {
            habits.get(userId).remove(habitTitle);
            if (habits.get(userId).isEmpty()) {
                habits.remove(userId);
            }
        }
    }

    /**
     * Отчистит хранилище. Используется для удобного тестирования.
     */
    @Override
    public void clear() {
        habits.clear();
    }

}
