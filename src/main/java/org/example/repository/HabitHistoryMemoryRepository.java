package org.example.repository;

import java.time.LocalDate;
import java.util.Map;

public interface HabitHistoryMemoryRepository {

    void mark(Long habitId, LocalDate date);

    void create(Long habitId, LocalDate date);

    void delete(Long habitId);

    Map<LocalDate, Boolean> getHabitHistory(Long habitId);

    Boolean getLocalDateMark(Long habitId, LocalDate date);

}
