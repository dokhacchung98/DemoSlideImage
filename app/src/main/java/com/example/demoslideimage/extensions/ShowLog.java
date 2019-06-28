package com.example.demoslideimage.extensions;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.demoslideimage.R;
import com.google.android.material.snackbar.Snackbar;

public class ShowLog {
    public static void ShowLog(Context context, View view, String message, boolean isSuccess) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getColor(isSuccess ? R.color.colorSuccess : R.color.colorError));
        TextView textView = snackBarView.findViewById(com.example.demoslideimage.R.id.snackbar_text);
        textView.setTextColor(context.getColor(R.color.colorWhite));
        snackbar.show();
    }
}
