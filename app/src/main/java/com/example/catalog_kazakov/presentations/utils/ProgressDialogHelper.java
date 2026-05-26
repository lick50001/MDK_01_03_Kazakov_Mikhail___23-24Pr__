package com.example.catalog_kazakov.presentations.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.catalog_kazakov.R;

public class ProgressDialogHelper {
    public AlertDialog progressDialog;
    public ProgressDialogHelper(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
    }
}
