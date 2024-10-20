package org.example;

import org.example.model.HabitHistoryMark;

import java.time.LocalDate;
import java.util.*;

public class HabitHistoryTestData {

    public static final LocalDate HISTORY_EXISTED_DATE = LocalDate.of(
            2024, 10, 18
    );

    public static final Boolean HISTORY_EXISTED_MARK = true;

    public static final int EXISTED_HISTORY_SIZE = 5;

    public static final int EXISTED_CURRENT_STREAK = 2;

    public static final int EXISTED_MAX_STREAK = 4;

    public static final float EXISTED_WEEK_STATISTIC = 85.71429f;

    public static final float EXISTED_MONTH_STATISTIC = 85.71429f;

    public static List<HabitHistoryMark> getExistedHistory() {
        List<HabitHistoryMark> history = new ArrayList<>();
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 15
                        ),
                        true)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 16
                        ),
                        true)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 17
                        ),
                        true)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 18
                        ),
                        true)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 19
                        ),
                        false)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 20
                        ),
                        true)
        );
        history.add(
                new HabitHistoryMark(
                        LocalDate.of(
                                2024, 10, 21
                        ),
                        true)
        );
        return history;
    }

}
