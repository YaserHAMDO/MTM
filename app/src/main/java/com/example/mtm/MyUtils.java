package com.example.mtm;

import android.content.Context;
import android.content.Intent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyUtils {

    public static String getCurrentDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the current date using the formatter
        return currentDate.format(formatter);
    }

    // Method to handle sharing the link
    public static void shareLink(String url, Context context) {
        // Create a new intent with the ACTION_SEND action
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        // Set the type of content to "text/plain"
        shareIntent.setType("text/plain");

        // Add the link to the intent as an extra
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);

        // Start the activity with the intent
        context.startActivity(Intent.createChooser(shareIntent, "Share link via"));
    }

}
