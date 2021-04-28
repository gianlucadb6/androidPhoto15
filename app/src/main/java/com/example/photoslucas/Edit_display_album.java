package com.example.photoslucas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Edit_display_album extends AppCompatActivity {

    public static final String ALBUM_INDEX = "albumIndex";
    public static final String ALBUM_NAME = "albumName";

    public static final int RESULT_DELETE = RESULT_FIRST_USER+1;
    public static final int RESULT_OPEN = RESULT_FIRST_USER + 2;

    private int albumIndex;
    private ArrayList <Album> albumList;
    private EditText albumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // get the fields
        albumName = findViewById(R.id.album_name);

        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));
        }


    }
    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

        public void rename(View view) {
            // gather all data from text fields
            String name = albumName.getText().toString();

            // pop up dialog if errors in input, and return
            // name and year are mandatory
           /*if (name == null) {
                Bundle bundle = new Bundle();
                bundle.putString(MovieDialogFragment.MESSAGE_KEY,
                        "Name and year are required");
                DialogFragment newFragment = new MovieDialogFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "badfields");
                return; // does not quit activity, just returns from method
            }
*/
            // make Bundle
            Bundle bundle = new Bundle();
            bundle.putInt(ALBUM_INDEX, albumIndex);
            bundle.putString(ALBUM_NAME,name);

            // send back to caller
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish(); // pops activity from the call stack, returns to parent

        }

        public void delete(View view){

            String name = albumName.getText().toString();

            // make Bundle
            Bundle bundle = new Bundle();
            bundle.putInt(ALBUM_INDEX, albumIndex);
            bundle.putString(ALBUM_NAME,name);

            // send back to caller
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_DELETE,intent);
            finish(); // pops activity from the call stack, returns to parent
        }

        public void open(View view){
            String name = albumName.getText().toString();

            // make Bundle
            Bundle bundle = new Bundle();
            bundle.putInt(ALBUM_INDEX, albumIndex);
            bundle.putString(ALBUM_NAME,name);

            // send back to caller
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OPEN,intent);
            finish(); // pops activity from the call stack, returns to parent
        }
}