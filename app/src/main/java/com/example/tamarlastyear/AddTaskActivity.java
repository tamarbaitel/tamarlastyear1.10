package com.example.tamarlastyear;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {

    Spinner spType;
    Spinner spSubject;
    Spinner spPriority;

    EditText etTitle;
    EditText etDueDate;
    EditText etAmount;

    Button btnSave;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task);

        spType = findViewById(R.id.spType);
        spSubject = findViewById(R.id.spSubject);
        spPriority = findViewById(R.id.spPriority);

        etTitle = findViewById(R.id.etTitle);
        etDueDate = findViewById(R.id.etDueDate);
        etAmount = findViewById(R.id.etAmount);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        String[] types = {
                "שיעורי בית",
                "מבחן"
        };

        String[] subjects = {
                "מתמטיקה",
                "אנגלית",
                "עברית",
                "היסטוריה",
                "אזרחות"
        };

        String[] priorities = {
                "גבוהה",
                "בינונית",
                "נמוכה"
        };

        setSpinner(spType, types);
        setSpinner(spSubject, subjects);
        setSpinner(spPriority, priorities);

        btnSave.setOnClickListener(v -> saveTask());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void setSpinner(
            Spinner spinner,
            String[] values) {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        values
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }

    private void saveTask() {

        String title =
                etTitle.getText().toString().trim();

        String dueDate =
                etDueDate.getText().toString().trim();

        String amount =
                etAmount.getText().toString().trim();

        if (title.equals("") ||
                dueDate.equals("") ||
                amount.equals("")) {

            Toast.makeText(
                    this,
                    "יש למלא את כל השדות",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int number;

        try {

            number = Integer.parseInt(amount);

            if (number <= 0) {
                throw new Exception();
            }

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "יש להכניס מספר תקין",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String type =
                spType.getSelectedItem().toString();

        String subject =
                spSubject.getSelectedItem().toString();

        String priority =
                spPriority.getSelectedItem().toString();

        Task task;

        if (type.equals("שיעורי בית")) {

            task = new HomeworkTask(
                    title,
                    subject,
                    priority,
                    dueDate,
                    number
            );

        } else {

            task = new ExamTask(
                    title,
                    subject,
                    priority,
                    dueDate,
                    number
            );
        }

        ArrayList<Task> tasks =
                TaskStorage.loadTasks(this);

        tasks.add(task);

        TaskStorage.saveTasks(
                this,
                tasks
        );

        Toast.makeText(
                this,
                "המשימה נשמרה",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}