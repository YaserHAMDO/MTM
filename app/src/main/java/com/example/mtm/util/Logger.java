package com.example.mtm.util;

import android.util.Log;

import com.example.mtm.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Logger {

    // Singleton pattern to ensure a single instance of the logger
    private static Logger instance;

    private Logger() {
        // Private constructor to prevent external instantiation
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logDebug(String className, String functionName, int type, Object object) {
        if (BuildConfig.DEBUG) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String requestJson = gson.toJson(object);

            String s;
            switch (type) {
                case 0:
                    s = "info";
                    break;
                case 1:
                    s = "request";
                    break;
                case 2:
                    s = "response";
                    break;
                case 3:
                    s = "failure";
                    break;
                default:
                    s = "";
            }

            Log.d("hasan: " + className, functionName + "/" + s + ":\n" + requestJson);
        }
    }


    public void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    public void logWarning(String tag, String message) {
        Log.w(tag, message);
    }

    public void logError(String tag, String message) {
        Log.e(tag, message);
    }
}