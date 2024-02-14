package com.example.mtm.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

    // Method to subtract a day from the current date and return it as a string
    public static String getPreviousDate(int days) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Subtract one day from the current date
        LocalDate previousDate = currentDate.minusDays(days);

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the previous date using the formatter
        return previousDate.format(formatter);
    }

    public static String getFirstDateOfMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Set the day of the month to 1 to get the first date of the month
        LocalDate firstDateOfMonth = currentDate.withDayOfMonth(1);

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the first date of the month using the formatter
        return firstDateOfMonth.format(formatter);
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

    public static void openLink(String url, Context context) {
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(urlIntent);
    }

}
