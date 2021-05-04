package com.example.photoslucas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class SearchOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void searchOne(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    public void searchAnd(View view){
        setResult(RESULT_CANCELED);
        finish();

    }

    public void searchOr(View view){
        setResult(RESULT_CANCELED);
        finish();
    }
}