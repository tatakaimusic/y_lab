package org.example.service.impl;

import org.example.model.Period;
import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.service.HabitHistoryService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;


public class HabitHistoryServiceImpl implements HabitHistoryService {

    private final HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository;
    private final HabitMemoryRepositoryImpl habitMemoryRepository;

    public HabitHistoryServiceImpl(
            HabitHistoryMemoryRepositoryImpl habitHistoryMemoryRepository,
            HabitMemoryRepositoryImpl habitMemoryRepository
    ) {
        this.habitHistoryMemoryRepository = habitHistoryMemoryRepository;
        this.habitMemoryRepository = habitMemoryRepository;
    }

    @Override
    public void mark(Long habitId, Long userId) {
        habitHistoryMemoryRepository.mark(habitId, LocalDate.now());
    }

    @Override
    public void mark(Long habitId, Long userId, LocalDate date) {
        habitHistoryMemoryRepository.mark(habitId, date);
    }

    @Override
    public void create(Long habitId) {
        habitHistoryMemoryRepository.create(habitId, LocalDate.now());
    }

    @Override
    public void create(Long habitId, LocalDate date) {
        habitHistoryMemoryRepository.create(habitId, date);
    }

    @Override
    public void delete(Long habitId) {
        habitHistoryMemoryRepository.delete(habitId);
    }

    @Override
    public Map<LocalDate, Boolean> getHabitHistory(Long habitId) {
        return habitHistoryMemoryRepository.getHabitHistory(habitId);
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) {
        return habitHistoryMemoryRepository.getLocalDateMark(habitId, date);
    }

    @Override
    public int getCurrentStreak(Long habitId) {
        Map<LocalDate, Boolean> history = habitHistoryMemoryRepository.getHabitHistory(habitId);
        int result = 0;
        for (int i = 1; i < history.size(); i++) {
            Boolean mark = history.get(LocalDate.now().minusDays(i));
            if (mark == null || !mark) {
                break;
            } else {
                result++;
            }
        }
        return result;
    }

    @Override
    public int getMaxStreak(Long habitId) {
        Map<LocalDate, Boolean> history = habitHistoryMemoryRepository.getHabitHistory(habitId);
        int result = 0;
        int temp = 0;
        for (int i = 1; i < history.size(); i++) {
            Boolean mark = history.get(LocalDate.now().minusDays(i));
            if (mark == null) {
                break;
            }
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
    public Float getPercentOfHabitForPeriod(Long habitId, Period period) {
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

    private Float getStatisticByDay(Long habitId) {
        Boolean result = getLocalDateMark(habitId, LocalDate.now().minusDays(1));
        if (result == null || !result) {
            return 0.0f;
        } else {
            return 100.0f;
        }
    }

    private Float getStatisticByWeek(Long habitId) {
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

    private Float getStatisticByMonth(Long habitId) {
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
