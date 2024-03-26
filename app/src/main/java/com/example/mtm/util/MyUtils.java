package com.example.mtm.util;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mtm.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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


    public static String changeDateFormat(String inputDate) {
        if (inputDate == null)
            return "";
        try {
            // Parse the input string into a LocalDate object
            LocalDate date = LocalDate.parse(inputDate);

            // Define the desired date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Format the date using the formatter
            return date.format(formatter);
        }
        catch (DateTimeParseException e) {
        // Handle the parsing exception
        e.printStackTrace();
        return null;
    }

    }



    public static String getDate(String input) {
        try {
            // Define the formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

            // Parse the string to LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);

            // Extract the date part
            LocalDate date = dateTime.toLocalDate();

            // Define the desired date format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Format the date using the formatter
            return date.format(dateFormatter);
        } catch (DateTimeParseException e) {
            // Handle the parsing exception
            e.printStackTrace();
            return null;
        }
    }

    public static String getTime(String input) {
        try {
            // Define the formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

            // Parse the string to LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);

            // Extract the time part
            LocalTime time = dateTime.toLocalTime();

            // Define the desired time format
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Format the time using the formatter
            return time.format(timeFormatter);
        } catch (DateTimeParseException e) {
            // Handle the parsing exception
            e.printStackTrace();
            return null;
        }
    }




    public static void showImageSourceBottomSheet(Context context) {


        //        https://www.section.io/engineering-education/bottom-sheet-dialogs-using-android-studio/

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.filter_pop_up_layout);

//        LinearLayout cameraLinearLayout = bottomSheetDialog.findViewById(R.id.cameraLinearLayout);
//        LinearLayout galleryLinearLayout = bottomSheetDialog.findViewById(R.id.galleryLinearLayout);
//        LinearLayout deleteLinearLayout = bottomSheetDialog.findViewById(R.id.deleteLinearLayout);
//        View view99 = bottomSheetDialog.findViewById(R.id.view99);


//        cameraLinearLayout.setOnClickListener(v -> {
//
//            bottomSheetDialog.dismiss();
//
//        });







//        if(bottomSheetDialog.getWindow() != null)
//            bottomSheetDialog.getWindow().setDimAmount(90);
        bottomSheetDialog.show();

//        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                // Instructions on bottomSheetDialog Dismiss
//            }
//        });

    }

    public static void showSubscriptionDialog(Context context) {

        Dialog loginDialog = new Dialog(context, R.style.LoginDialog);
        loginDialog.setContentView(R.layout.report_pop_up_layout);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.getWindow().setGravity(Gravity.CENTER);

        TextView continue_privacy = loginDialog.findViewById(R.id.hyperlink);
        continue_privacy.setMovementMethod(LinkMovementMethod.getInstance());

        continue_privacy.setOnClickListener(view -> {
            loginDialog.cancel();
        });

        loginDialog.setOnDismissListener(dialogInterface -> {


        });

        loginDialog.setCancelable(true);

        //   loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setCanceledOnTouchOutside(true);

        loginDialog.show();

    }

    public static void showFilterDialog(Context context) {

        Dialog loginDialog = new Dialog(context, R.style.LoginDialog);
        loginDialog.setContentView(R.layout.filter_pop_up_layout);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.getWindow().setGravity(Gravity.BOTTOM);

//        TextView continue_privacy = loginDialog.findViewById(R.id.hyperlink);
//        continue_privacy.setMovementMethod(LinkMovementMethod.getInstance());
//
//        continue_privacy.setOnClickListener(view -> {
//            loginDialog.cancel();
//        });

        loginDialog.setOnDismissListener(dialogInterface -> {


        });

        loginDialog.setCancelable(true);

        //   loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setCanceledOnTouchOutside(true);

        loginDialog.show();

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
