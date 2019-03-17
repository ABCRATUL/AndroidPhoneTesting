package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Message {
    /**
     * Method to show Toast message.
     *
     * @param context: The context for the toast message.
     * @param msg:     The Message to show.
     * @param length:  The duration of the toast. "" for SHORT or anything else for LONG.
     */
    public static void toastMessage(Context context, String msg, String length) {
        if (length.equalsIgnoreCase("long")) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void logMessage(String tag, String msg) {
        Log.d(tag, msg);
    }
}
