package org.example.service;

import org.example.model.Period;
import org.example.repository.HabitHistoryRepository;
import org.example.repository.HabitRepository;
import org.example.service.impl.HabitHistoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.example.HabitHistoryTestData.*;
import static org.example.HabitTestData.*;
import static org.mockito.Mockito.when;

public class HabitHistoryServiceTest {

    private static HabitRepository habitRepository;
    private static HabitHistoryRepository habitHistoryRepository;
    private static HabitHistoryService habitHistoryService;

    @BeforeAll
    public static void init() {
        habitRepository = Mockito.mock(HabitRepository.class);
        habitHistoryRepository = Mockito.mock(HabitHistoryRepository.class);
        habitHistoryService = new HabitHistoryServiceImpl(
                habitRepository, habitHistoryRepository
        );
    }

    @Test
    @DisplayName("Getting current streak test")
    public void getCurrentStreak() throws SQLException {
        when(habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID))
                .thenReturn(getExistedHistory());
        int streak = habitHistoryService.getCurrentStreak(HABIT_EXISTED_ID);
        assertThat(streak).isEqualTo(EXISTED_CURRENT_STREAK);
    }

    @Test
    @DisplayName("Getting max streak test")
    public void getMaxStreak() throws SQLException {
        when(habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID))
                .thenReturn(getExistedHistory());
        int streak = habitHistoryService.getMaxStreak(HABIT_EXISTED_ID);
        assertThat(streak).isEqualTo(EXISTED_MAX_STREAK);
    }

    @Test
    @DisplayName("Getting statistic by week test")
    public void getStatisticByWeek() throws SQLException {
        when(habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID))
                .thenReturn(getExistedHistory());
        assertThat(
                habitHistoryService.getPercentOfHabitForPeriod(HABIT_EXISTED_ID, Period.WEEK)
        ).isEqualTo(EXISTED_WEEK_STATISTIC);
    }

    @Test
    @DisplayName("Getting statistic by month test")
    public void getStatisticByMonth() throws SQLException {
        when(habitHistoryRepository.getHabitHistory(HABIT_EXISTED_ID))
                .thenReturn(getExistedHistory());
        assertThat(
                habitHistoryService.getPercentOfHabitForPeriod(HABIT_EXISTED_ID, Period.MONTH)
        ).isEqualTo(EXISTED_MONTH_STATISTIC);
    }

}
