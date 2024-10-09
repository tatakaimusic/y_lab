package org.example.repository;

import java.time.LocalDate;
import java.util.List;

public interface HabitHistoryMemoryRepository {

    void create(Long habitId, LocalDate date);

    List<LocalDate> getHabitHistory(Long habitId);

}
