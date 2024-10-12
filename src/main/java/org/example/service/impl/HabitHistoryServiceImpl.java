package org.example.service.impl;

import org.example.repository.impl.HabitHistoryMemoryRepositoryImpl;
import org.example.repository.impl.HabitMemoryRepositoryImpl;
import org.example.service.HabitHistoryService;

import java.time.LocalDate;
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
        habitHistoryMemoryRepository.mark(habitId);
    }

    @Override
    public void create(Long habitId) {
        habitHistoryMemoryRepository.create(habitId);
    }

    @Override
    public Map<LocalDate, Boolean> getHabitHistory(Long habitId) {
        return habitHistoryMemoryRepository.getHabitHistory(habitId);
    }

    @Override
    public Boolean getLocalDateMark(Long habitId, LocalDate date) {
        return habitHistoryMemoryRepository.getLocalDateMark(habitId, date);
    }

}
