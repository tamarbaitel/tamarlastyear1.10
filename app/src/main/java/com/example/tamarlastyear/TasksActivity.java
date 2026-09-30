package com.example.tamarlastyear;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    TextView tvHello;
    TextView tvStats;
    TextView tvEmpty;

    Spinner spFilter;

    ListView lvTasks;

    Button btnAdd;
    Button btnLogout;

    ArrayList<Task> tasks;
    ArrayList<Task> displayedTasks;

    ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tasks);

        tvHello = findViewById(R.id.tvHello);
        tvStats = findViewById(R.id.tvStats);
        tvEmpty = findViewById(R.id.tvEmpty);

        spFilter = findViewById(R.id.spFilter);

        lvTasks = findViewById(R.id.lvTasks);

        btnAdd = findViewById(R.id.btnAdd);
        btnLogout = findViewById(R.id.btnLogout);

        tasks = TaskStorage.loadTasks(this);

        displayedTasks = new ArrayList<>();

        String name = TaskStorage.loadName(this);

        tvHello.setText("שלום " + name + "!");

        String[] filters = {
                "כל המקצועות",
                "מתמטיקה",
                "אנגלית",
                "עברית",
                "היסטוריה",
                "אזרחות"
        };

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        filters
                );

        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFilter.setAdapter(spinnerAdapter);

        adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        displayedTasks
                );

        lvTasks.setAdapter(adapter);

        updateList("כל המקצועות");

        spFilter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String filter =
                                parent.getItemAtPosition(position).toString();

                        updateList(filter);
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });

        btnAdd.setOnClickListener(v -> {

            Intent intent =
                    new Intent(TasksActivity.this,
                            AddTaskActivity.class);

            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            finish();
        });

        lvTasks.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Task selectedTask =
                            displayedTasks.get(position);

                    int realPosition =
                            tasks.indexOf(selectedTask);

                    Intent intent =
                            new Intent(
                                    TasksActivity.this,
                                    TaskDetailsActivity.class
                            );

                    intent.putExtra("position", realPosition);

                    startActivity(intent);
                });

        lvTasks.setOnItemLongClickListener(
                (parent, view, position, id) -> {

                    Task selectedTask =
                            displayedTasks.get(position);

                    new AlertDialog.Builder(this)
                            .setTitle("מחיקת משימה")
                            .setMessage(
                                    "האם למחוק את " +
                                            selectedTask.getTitle() + "?"
                            )
                            .setPositiveButton("מחיקה",
                                    (dialog, which) -> {

                                        tasks.remove(selectedTask);

                                        TaskStorage.saveTasks(
                                                this,
                                                tasks
                                        );

                                        updateList(
                                                spFilter
                                                        .getSelectedItem()
                                                        .toString()
                                        );
                                    })
                            .setNegativeButton(
                                    "ביטול",
                                    null
                            )
                            .show();

                    return true;
                });
    }

    private void updateList(String filter) {

        displayedTasks.clear();

        for (Task task : tasks) {

            if (filter.equals("כל המקצועות") ||
                    task.getSubject().equals(filter)) {

                displayedTasks.add(task);
            }
        }

        adapter.notifyDataSetChanged();

        if (displayedTasks.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        updateStats();
    }

    private void updateStats() {

        int done = 0;
        int points = 0;

        for (Task task : tasks) {

            if (task.isDone()) {
                done++;
                points += task.getPoints();
            }
        }

        tvStats.setText(
                "משימות: " + tasks.size() +
                        " | הושלמו: " + done +
                        " | נקודות: " + points
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        tasks = TaskStorage.loadTasks(this);

        if (spFilter != null) {

            updateList(
                    spFilter.getSelectedItem().toString()
            );
        }
    }
}