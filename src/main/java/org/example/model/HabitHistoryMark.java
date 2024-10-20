package org.example.model;

import java.time.LocalDate;

/**
 * Модель истории привычки.
 */
public class HabitHistoryMark {

    private Long id;
    private LocalDate date;
    private Boolean isDone;

    public HabitHistoryMark(Long id, LocalDate date, Boolean isDone) {
        this.id = id;
        this.date = date;
        this.isDone = isDone;
    }

    public HabitHistoryMark(LocalDate date, Boolean isDone) {
        this.date = date;
        this.isDone = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

}
