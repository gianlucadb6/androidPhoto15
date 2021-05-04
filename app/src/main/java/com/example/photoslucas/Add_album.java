package com.example.photoslucas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Add_album extends AppCompatActivity {


    public static final int RESULT_ADD = RESULT_FIRST_USER + 3;
    public static final String ALBUM_NAME = "albumName";

    private ArrayList<Album> albumList;
    private EditText albumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the fields
        albumName = findViewById(R.id.album_name);


    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void add(View view) {

        String name = albumName.getText().toString();

        // make Bundle
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME,name);

        // send back to caller
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_ADD,intent);
        finish(); // pops activity from the call stack, returns to parent
    }
}