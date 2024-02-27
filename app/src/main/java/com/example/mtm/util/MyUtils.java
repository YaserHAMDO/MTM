package com.example.mtm.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mtm.R;

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


    public static String changeDateFormat(String inputDate) {
        // Parse the input string into a LocalDate object
        LocalDate date = LocalDate.parse(inputDate);

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Format the date using the formatter
        return date.format(formatter);
    }


    public static void showPopupDialog(Context context) {


        Dialog loginDialog = new Dialog(context, R.style.LoginDialog);
        loginDialog.setContentView(R.layout.report_pop_up_layout);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.getWindow().setGravity(Gravity.CENTER);

        TextView continue_privacy = loginDialog.findViewById(R.id.hyperlink);
        continue_privacy.setMovementMethod(LinkMovementMethod.getInstance());

        continue_privacy.setOnClickListener(view -> {
            loginDialog.cancel();
        });

//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginDialog.cancel();
//            }
//        });

        loginDialog.setOnDismissListener(dialogInterface -> {


        });

        loginDialog.setCancelable(true);

        //   loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setCanceledOnTouchOutside(true);

        loginDialog.show();


//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Not:")
//                .setMessage("")
////                .setPositiveButton("Okundu", (dialog, which) -> {
//////                    // Update item as read
//////                    mItems.get(position).setRead(true);
//////                    // Notify adapter about the change
//////                    notifyItemChanged(position);
////////                    listener.onItemClick(mItems.get(position).getId());
////                })
//                .setCancelable(true)
//                .show();
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
