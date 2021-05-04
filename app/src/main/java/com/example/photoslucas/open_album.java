package com.example.photoslucas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;


public class open_album extends AppCompatActivity {

    static final int REQUEST_IMAGE_GET = 1;
    public static final int RESULT_BACK = RESULT_FIRST_USER+1;
    public static final int RESULT_REMOVE = RESULT_FIRST_USER + 2;
    public static final int RESULT_MOVE = RESULT_FIRST_USER + 3;

    public static final String ALBUM_INDEX = "albumIndex";
    public static final String PHOTO_INDEX = "photoIndex";
    public static final String ALBUM_NAME = "albumName";


    GridView gridView;
    private Album currAlbum;
    private Albums albums;
    private String albumName;
    private int currAlbumIndex;
    final Context context = this;
    String fileName = "albums.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_album_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String fileName = this.getApplicationInfo().dataDir + "/albums.dat";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            currAlbumIndex = bundle.getInt(ALBUM_INDEX);
            albums = (Albums) bundle.getSerializable("albums");
            currAlbum = albums.albumList.get(currAlbumIndex);
            albumName = currAlbum.name;
        }



        gridView = (GridView) findViewById(R.id.photolist_grid_view);

        gridView.setAdapter(new PhotoListAdapter(this, currAlbum.photoList));


       // Toast.makeText(open_album.this, currAlbum.photoList.size(), Toast.LENGTH_SHORT).show();



        gridView.setOnItemClickListener((p, V, pos, id) ->
                showPhoto(pos));


    }

    public void showPhoto(int pos){
        Bundle bundle = new Bundle();
        bundle.putInt(Photo_Display.PHOTO_INDEX, pos);
        bundle.putSerializable(Photo_Display.ALBUM, currAlbum);
        bundle.putSerializable("albums", albums);
        Intent intent = new Intent(this, Photo_Display.class);
        intent.putExtras(bundle);
        startActivity(intent);
        return;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.slideshow_menu,menu);
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                selectImage();
                return true;
            case R.id.action_slideshow:
                Bundle bundle = new Bundle();
                bundle.putSerializable("photos", currAlbum.photoList);
                Intent intent = new Intent(this, Slideshow.class);
                intent.putExtras(bundle);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String fileName = this.getApplicationInfo().dataDir + "/albums.dat";

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            currAlbum.photoList.add(new Photo(fullPhotoUri.toString()));
            try {
                Albums.writeApp(albums, fileName);
                Toast.makeText(open_album.this, "Added Photo Worked", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(open_album.this, "Added PHOTO FAILED", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        //Back Button Clicked from Photodisplay
        else if(resultCode == RESULT_BACK){
            return;
        }
        //Remove Button Clicked from Photodisplay
        else if(resultCode == RESULT_REMOVE){
            //Do work to remove photo from albums.get(albumIndex).photoList.get(photoIndex)
        }
        //Move Button Clicked from Photodisplay
        else if(resultCode == RESULT_MOVE){
            //Do work to move photo from albums.get(albumIndex).photoList.get(photoIndex) to other album
            try {
                albums = (Albums)Albums.readApp(fileName);
                currAlbum = albums.albumList.get(currAlbumIndex);
                System.out.println("moved worked");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        gridView.setAdapter(new PhotoListAdapter(this, currAlbum.photoList));
    }


}