package org.example.service.impl;

import org.example.model.HabitHistoryMark;
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
    public List<HabitHistoryMark> getHabitHistory(Long habitId) throws SQLException {
        return habitHistoryRepository.getHabitHistory(habitId);
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) throws SQLException {
        return habitHistoryRepository.getLocalDateMark(habitId, date);
    }

    @Override
    public int getCurrentStreak(Long habitId) throws SQLException {
        List<HabitHistoryMark> history = habitHistoryRepository.getHabitHistory(habitId);
        Collections.reverse(history);
        int result = 0;
        for (HabitHistoryMark mark : history) {
            if (!mark.getDone()) {
                break;
            } else {
                result++;
            }
        }
        return result;
    }

    @Override
    public int getMaxStreak(Long habitId) throws SQLException {
        List<HabitHistoryMark> history = habitHistoryRepository.getHabitHistory(habitId);
        int result = 0;
        int temp = 0;
        for (HabitHistoryMark mark : history) {
            if (mark.getDone()) {
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
        List<HabitHistoryMark> history = getHabitHistory(habitId);
        Collections.reverse(history);
        int countOfDays = Math.min(history.size(), 7);
        for (int i = 0; i < countOfDays; i++) {
            if (history.get(i).getDone()) {
                completedHabitsCounter++;
            }
        }
        return getPercent(completedHabitsCounter, countOfDays);
    }

    private Float getStatisticByMonth(Long habitId) throws SQLException {
        Month month = LocalDate.now().getMonth();
        int year = LocalDate.now().getYear();
        int completedHabitsCounter = 0;
        int countOfDates = 0;
        List<HabitHistoryMark> history = getHabitHistory(habitId);
        Collections.reverse(history);
        for (HabitHistoryMark mark : history) {
            if (mark.getDate().getMonth().equals(month) && mark.getDate().getYear() == year) {
                countOfDates++;
                if (mark.getDone()) {
                    completedHabitsCounter++;
                }
            } else {
                break;
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
