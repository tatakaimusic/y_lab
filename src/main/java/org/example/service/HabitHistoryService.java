package org.example.service;

import org.example.model.Period;

import java.time.LocalDate;
import java.util.Map;

public interface HabitHistoryService {

    void mark(Long habitId, Long userId);

    void mark(Long habitId, Long userId, LocalDate date);

    void create(Long habitId);

    void create(Long habitId, LocalDate date);

    void delete(Long habitId);

    Map<LocalDate, Boolean> getHabitHistory(Long habitId);

    Boolean getLocalDateMark(Long habitId, LocalDate date);

    int getCurrentStreak(Long habitId);

    int getMaxStreak(Long habitId);

    Float getPercentOfHabitForPeriod(Long habitId, Period period);

}
