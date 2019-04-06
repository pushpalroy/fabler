package com.techradge.fabler.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showSnackBar(String message) {
        showSnackBar(message, findViewById(android.R.id.content));
    }

    protected void showSnackBar(String message, View view) {
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackBar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackBar.show();
    }

    protected void showToastMessage(String message, int length) {
        Toast.makeText(getApplicationContext(), message, length).show();
    }

    protected void setUpActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitle(String title) {
        if (title == null) return;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
