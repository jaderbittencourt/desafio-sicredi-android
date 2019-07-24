package com.jaderbittencourt.sicredidigital.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.jaderbittencourt.sicredidigital.R;

public class AlertUtil {

    public static void showAlert(Activity activity, String message) {
        try {
            AlertDialog.Builder alertDialogBuilder  = setAlert(activity, message);
            alertDialogBuilder.setNegativeButton(activity.getResources().getString(R.string.ok), null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {
            Log.e(AlertUtil.class.toString(), e.getMessage());
        }
    }

    private static AlertDialog.Builder setAlert(Activity activity, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage(message);
        return alertDialogBuilder;
    }
}
