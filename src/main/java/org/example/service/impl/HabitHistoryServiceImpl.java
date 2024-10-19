package org.example.service.impl;

import org.example.model.Period;
import org.example.repository.HabitHistoryRepository;
import org.example.repository.HabitRepository;
import org.example.service.HabitHistoryService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class HabitHistoryServiceImpl implements HabitHistoryService {

    private final HabitRepository habitRepository;
    private final HabitHistoryRepository habitHistoryRepository;

    public HabitHistoryServiceImpl(HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public void mark(Long habitId, Long userId) throws SQLException {
        habitHistoryRepository.mark(habitId, LocalDate.now());
    }

    @Override
    public void mark(Long habitId, Long userId, LocalDate date) throws SQLException {
        habitHistoryRepository.mark(habitId, date);
    }

    @Override
    public void create(Long habitId) throws SQLException {
        habitHistoryRepository.create(habitId, LocalDate.now());
    }

    @Override
    public void create(Long habitId, LocalDate date) throws SQLException {
        habitHistoryRepository.create(habitId, date);
    }

    @Override
    public void delete(Long habitId) throws SQLException {
        habitHistoryRepository.delete(habitId);
    }

    @Override
    public Map<LocalDate, Boolean> getHabitHistory(Long habitId) throws SQLException {
        return habitHistoryRepository.getHabitHistory(habitId);
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) throws SQLException {
        return habitHistoryRepository.getLocalDateMark(habitId, date);
    }

    @Override
    public int getCurrentStreak(Long habitId) throws SQLException {
        List<Boolean> history = new ArrayList<>(habitHistoryRepository.getHabitHistory(habitId).values());
        Collections.reverse(history);
        int result = 0;
        for (Boolean mark : history) {
            if (!mark) {
                break;
            } else {
                result++;
            }
        }
        return result;
    }

    @Override
    public int getMaxStreak(Long habitId) throws SQLException {
        List<Boolean> history = new ArrayList<>(habitHistoryRepository.getHabitHistory(habitId).values());
        int result = 0;
        int temp = 0;
        for (Boolean mark : history) {
            if (mark) {
                temp++;
            } else {
                temp = 0;
            }
            result = Math.max(temp, result);
        }
        return result;
    }

    @Override
    public Float getPercentOfHabitForPeriod(Long habitId, Period period) throws SQLException {
        Float result = 0.0f;
        switch (period) {
            case DAY -> {
                result = getStatisticByDay(habitId);
            }
            case WEEK -> {
                result = getStatisticByWeek(habitId);
            }
            case MONTH -> {
                result = getStatisticByMonth(habitId);
            }
        }
        return result;
    }

    private Float getStatisticByDay(Long habitId) throws SQLException {
        Boolean result = getLocalDateMark(habitId, LocalDate.now().minusDays(1));
        if (result == null || !result) {
            return 0.0f;
        } else {
            return 100.0f;
        }
    }

    private Float getStatisticByWeek(Long habitId) throws SQLException {
        int completedHabitsCounter = 0;
        int countOfDates = 0;
        Map<LocalDate, Boolean> dates = getHabitHistory(habitId);
        for (int i = 1; i < 8; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            if (dates.containsKey(date)) {
                countOfDates++;
                if (dates.get(date)) {
                    completedHabitsCounter++;
                }
            } else {
                break;
            }
        }
        return getPercent(completedHabitsCounter, countOfDates);
    }

    private Float getStatisticByMonth(Long habitId) throws SQLException {
        Month month = LocalDate.now().getMonth();
        int year = LocalDate.now().getYear();
        int completedHabitsCounter = 0;
        int countOfDates = 0;
        Map<LocalDate, Boolean> dates = getHabitHistory(habitId);
        for (Map.Entry<LocalDate, Boolean> entry : dates.entrySet()) {
            if (entry.getKey().getMonth() == month && entry.getKey().getYear() == year) {
                countOfDates++;
                if (entry.getValue()) {
                    completedHabitsCounter++;
                }
            }
        }
        return getPercent(completedHabitsCounter, countOfDates);
    }

    private Float getPercent(int firstNum, int secondNum) {
        if (secondNum == 0) {
            return 0.0f;
        } else {
            return (float) firstNum / secondNum * 100.0f;
        }
    }

}
