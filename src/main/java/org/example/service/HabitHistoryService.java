package org.example.service;

import java.time.LocalDate;
import java.util.Map;

public interface HabitHistoryService {

    void mark(Long habitId, Long userId);

    void create(Long habitId);

    Map<LocalDate, Boolean> getHabitHistory(Long habitId);

    Boolean getLocalDateMark(Long habitId, LocalDate date);

}
