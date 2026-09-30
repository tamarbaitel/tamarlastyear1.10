package com.example.tamarlastyear;

public class Task implements Rewardable {

    protected String title;
    protected String subject;
    protected String priority;
    protected String dueDate;
    protected boolean done;

    public Task(String title, String subject, String priority, String dueDate) {
        this.title = title;
        this.subject = subject;
        this.priority = priority;
        this.dueDate = dueDate;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    // בונוס לפי רמת עדיפות
    protected int getPriorityBonus() {

        if (priority.equals("גבוהה")) {
            return 5;
        }

        if (priority.equals("בינונית")) {
            return 3;
        }

        return 1;
    }

    @Override
    public int getPoints() {
        return getPriorityBonus();
    }

    @Override
    public String toString() {
        String status;

        if (done) {
            status = "✓";
        } else {
            status = "○";
        }

        return status + " " + title +
                " | " + subject +
                " | " + dueDate;
    }
}