package org.example.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Помошник для работы с датой.
 */
public class DateHelper {

    /**
     * Преобразоывает LocalDate в Timestamp. Время устанавливается 00:00:00.
     * @param localDate
     * @return
     */
    public static Timestamp localDateToTimestamp(LocalDate localDate) {
        return Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.MIDNIGHT));
    }

}
