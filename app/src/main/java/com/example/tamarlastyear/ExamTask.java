package com.example.tamarlastyear;

public class ExamTask extends Task {

    private int topics;

    public ExamTask(String title, String subject, String priority,
                    String dueDate, int topics) {

        super(title, subject, priority, dueDate);
        this.topics = topics;
    }

    public int getTopics() {
        return topics;
    }

    @Override
    public int getPoints() {
        return 10 + topics * 5 + getPriorityBonus();
    }

    @Override
    public String toString() {
        return super.toString() +
                " | נושאים: " + topics;
    }
}