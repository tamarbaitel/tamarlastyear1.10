package com.example.tamarlastyear;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskStorage {

    private static final String PREFS_NAME = "TaskPrefs";
    private static final String TASKS_KEY = "tasks";
    private static final String NAME_KEY = "name";

    public static void saveTasks(Context context, ArrayList<Task> tasks) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = gson.toJson(tasks);

        prefs.edit()
                .putString(TASKS_KEY, json)
                .apply();
    }

    public static ArrayList<Task> loadTasks(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = prefs.getString(TASKS_KEY, "");

        if (json.equals("")) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Task>>() {}.getType();

        ArrayList<Task> tasks = gson.fromJson(json, type);

        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        return tasks;
    }

    public static void saveName(Context context, String name) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        prefs.edit()
                .putString(NAME_KEY, name)
                .apply();
    }

    public static String loadName(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return prefs.getString(NAME_KEY, "");
    }

    public static void reset(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        prefs.edit().clear().apply();
    }
}