package com.example.tamarlastyear;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnEnter;
    Button btnReset;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnEnter = findViewById(R.id.btnEnter);
        btnReset = findViewById(R.id.btnReset);
        tvWelcome = findViewById(R.id.tvWelcome);

        String oldName = TaskStorage.loadName(this);

        if (!oldName.equals("")) {
            tvWelcome.setText("ברוך שובך, " + oldName);
            etName.setText(oldName);
        }

        btnEnter.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();

            if (name.length() < 2) {

                Toast.makeText(
                        this,
                        "השם חייב להכיל לפחות 2 תווים",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            TaskStorage.saveName(this, name);

            Intent intent =
                    new Intent(MainActivity.this, TasksActivity.class);

            startActivity(intent);
        });

        btnReset.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("איפוס נתונים")
                    .setMessage("האם אתה בטוח שברצונך למחוק את כל הנתונים?")
                    .setPositiveButton("כן", (dialog, which) -> {

                        TaskStorage.reset(this);

                        etName.setText("");
                        tvWelcome.setText("מתכנן שיעורי בית");

                        Toast.makeText(
                                this,
                                "כל הנתונים נמחקו",
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .setNegativeButton("ביטול", null)
                    .show();
        });
    }
}