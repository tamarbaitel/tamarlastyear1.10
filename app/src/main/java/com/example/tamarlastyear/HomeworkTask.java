package com.example.tamarlastyear;


public class HomeworkTask extends Task {

    private int exercises;

    public HomeworkTask(String title, String subject, String priority,
                        String dueDate, int exercises) {

        super(title, subject, priority, dueDate);
        this.exercises = exercises;
    }

    public int getExercises() {
        return exercises;
    }

    @Override
    public int getPoints() {
        return exercises * 2 + getPriorityBonus();
    }

    @Override
    public String toString() {
        return super.toString() +
                " | תרגילים: " + exercises;
    }
}
