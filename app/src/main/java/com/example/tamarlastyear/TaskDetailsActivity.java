package com.example.tamarlastyear;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView tvDetails;

    Button btnToggleDone;
    Button btnDelete;
    Button btnBack;

    ArrayList<Task> tasks;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_details);

        tvDetails = findViewById(R.id.tvDetails);

        btnToggleDone =
                findViewById(R.id.btnToggleDone);

        btnDelete =
                findViewById(R.id.btnDelete);

        btnBack =
                findViewById(R.id.btnBack);

        position =
                getIntent().getIntExtra(
                        "position",
                        -1
                );

        tasks = TaskStorage.loadTasks(this);

        if (position >= 0 &&
                position < tasks.size()) {

            showTask();
        }

        btnToggleDone.setOnClickListener(v -> {

            Task task = tasks.get(position);

            task.setDone(!task.isDone());

            TaskStorage.saveTasks(
                    this,
                    tasks
            );

            showTask();
        });

        btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("מחיקת משימה")
                    .setMessage("האם למחוק את המשימה?")
                    .setPositiveButton(
                            "מחיקה",
                            (dialog, which) -> {

                                tasks.remove(position);

                                TaskStorage.saveTasks(
                                        this,
                                        tasks
                                );

                                Toast.makeText(
                                        this,
                                        "המשימה נמחקה",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();
                            })
                    .setNegativeButton(
                            "ביטול",
                            null
                    )
                    .show();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void showTask() {

        Task task = tasks.get(position);

        String type;

        if (task instanceof HomeworkTask) {
            type = "שיעורי בית";
        } else {
            type = "מבחן";
        }

        String details =
                "סוג: " + type +
                        "\n\nשם: " + task.getTitle() +
                        "\n\nמקצוע: " + task.getSubject() +
                        "\n\nעדיפות: " + task.getPriority() +
                        "\n\nתאריך: " + task.getDueDate() +
                        "\n\nבוצע: " +
                        (task.isDone() ? "כן" : "לא") +
                        "\n\nנקודות: " +
                        task.getPoints();

        if (task instanceof HomeworkTask) {

            HomeworkTask homework =
                    (HomeworkTask) task;

            details +=
                    "\n\nמספר תרגילים: " +
                            homework.getExercises();

        } else if (task instanceof ExamTask) {

            ExamTask exam =
                    (ExamTask) task;

            details +=
                    "\n\nמספר נושאים: " +
                            exam.getTopics();
        }

        tvDetails.setText(details);

        if (task.isDone()) {

            btnToggleDone.setText(
                    "סמן כלא בוצע"
            );

        } else {

            btnToggleDone.setText(
                    "סמן כבוצע"
            );
        }
    }
}