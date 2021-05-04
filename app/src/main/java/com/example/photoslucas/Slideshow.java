package com.example.photoslucas;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class Slideshow extends AppCompatActivity {

    private ArrayList<Photo> photos;
    public int index;

    private ImageView picture;
    private Button next, prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        picture = (ImageView)findViewById(R.id.picture);
        next = (Button)findViewById(R.id.next);
        prev = (Button)findViewById(R.id.prev);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            photos = (ArrayList<Photo>)bundle.getSerializable("photos");
            index = 0;
            Photo currPhoto = photos.get(index);
            Uri currUri = Uri.parse(currPhoto.path);
            picture.setImageURI(currUri);

        }else {
            finish();
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == 0) {
                    index = photos.size()-1;
                }else {
                    index -= 1;
                }
                try {
                    Photo currPhoto = (Photo) photos.get(index);
                    Uri currUri = Uri.parse(currPhoto.path);
                    picture.setImageURI(currUri);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == photos.size()-1) {
                    index = 0;
                }else {
                    index += 1;
                }
                try {
                    Photo currPhoto = (Photo) photos.get(index);
                    Uri currUri = Uri.parse(currPhoto.path);
                    picture.setImageURI(currUri);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}