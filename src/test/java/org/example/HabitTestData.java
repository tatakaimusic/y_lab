package org.example;

import org.example.model.Habit;
import org.example.model.HabitPeriod;

import java.time.LocalDate;
import java.util.List;


public class HabitTestData {

    public static final String HABIT_TITLE = "new title";
    public static final String HABIT_DESCRIPTION = "new description";

    public static final Habit NEW_HABIT = new Habit(HABIT_TITLE, HABIT_DESCRIPTION, HabitPeriod.DAILY);

    public static final Long HABIT_EXISTED_ID = 100L;
    public static final String HABIT_EXISTED_TITLE = "first habit";
    public static final String HABIT_EXISTED_DESCRIPTION = "First habit description";
    public static final LocalDate HABIT_EXISTED_DATE = LocalDate.of(2024, 10, 14);

    public static final Habit EXISTED_HABIT = new Habit(
            HABIT_EXISTED_ID, HABIT_EXISTED_TITLE, HABIT_EXISTED_DESCRIPTION, HabitPeriod.DAILY, HABIT_EXISTED_DATE
    );

    public static List<Habit> getAllHabitsByExistedUser() {
        return List.of(EXISTED_HABIT);
    }

    public static final String HABIT_UPDATED_TITLE = "updated title";

    public static Habit getNewHabit() {
        return new Habit(HABIT_TITLE, HABIT_DESCRIPTION, HabitPeriod.DAILY);
    }

    public static Habit getExistedHabit() {
        return new Habit(HABIT_EXISTED_TITLE, HABIT_EXISTED_DESCRIPTION, HabitPeriod.DAILY);
    }

}
