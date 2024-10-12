package org.example.model;


import java.time.LocalDate;
import java.util.Objects;

public class Habit {

    private Long id;
    private String title;
    private String description;
    private HabitPeriod period;
    private LocalDate createDate;


    public Habit(Long id, String title, String description, HabitPeriod period) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.period = period;
    }

    public Habit(String title, String description, HabitPeriod period) {
        this.title = title;
        this.description = description;
        this.period = period;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HabitPeriod getPeriod() {
        return period;
    }

    public void setPeriod(HabitPeriod period) {
        this.period = period;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(id, habit.id)
                && Objects.equals(title, habit.title)
                && Objects.equals(description, habit.description)
                && period == habit.period
                && Objects.equals(createDate, habit.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, period, createDate);
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", period=" + period +
                ", createDate=" + createDate +
                '}';
    }

}


