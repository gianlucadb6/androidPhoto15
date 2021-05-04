package com.example.photoslucas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;


public class Photo_Display extends AppCompatActivity {

    public static final int RESULT_REMOVE = RESULT_FIRST_USER+1;
    public static final int RESULT_MOVE = RESULT_FIRST_USER + 3;

    public static final String MOVE_ALBUM = "moveAlbum";
    public static final String PHOTO_INDEX = "photoIndex";
    public static final String ALBUM = "album";
    public static final String ALBUMS = "albums";

    private Albums albums;
    private Album currAlbum;
    private Photo currPhoto;
    private int currPhotoIndex;
    private String moveTo = "";
    private String tag = "";
    private int selectedTag;
    private String fileName;

    private ImageView picture;
    private ListView tags;
    private ArrayList<String> arrayList;
    private Button addTag, remTag, move, display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fileName = this.getApplicationInfo().dataDir + "/albums.dat";
        picture = (ImageView)findViewById(R.id.picture);
        tags = (ListView)findViewById(R.id.tags);
        move = (Button)findViewById(R.id.movePhoto);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            currPhotoIndex = bundle.getInt(PHOTO_INDEX);
            albums = (Albums)bundle.getSerializable(ALBUMS);
            currAlbum = (Album)bundle.get(ALBUM);
            currPhoto = currAlbum.photoList.get(currPhotoIndex);
            Uri currUri = Uri.parse(currPhoto.path);
            picture.setImageURI(currUri);
        }

        arrayList = new ArrayList<String>();

        for (Tag t : currPhoto.tagList) {
            arrayList.add(t.toString());
        }
        tags = findViewById(R.id.tags);
        tags.setAdapter(
                new ArrayAdapter<String>(this, R.layout.photo_display, arrayList));
        tags.setOnItemClickListener((p, V, pos, id) ->
                savePos(pos));
    }

    public void movePhoto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Move photo to...");
        String arr[] = new String[albums.albumList.size()];
        for(int i = 0; i < albums.albumList.size(); ++i) {
            arr[i] = albums.albumList.get(i).name;
        }
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTo = arr[which];
                for(Album a : albums.albumList){
                    if(a.name.equals(moveTo)) {
                        a.photoList.add(currPhoto);
                        currAlbum.photoList.remove(currPhotoIndex);
                        try {
                            Albums.writeApp(albums, fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setResult(RESULT_MOVE);
                        finish();
                        break;
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void addTag(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Tag");
        String types[] = {"Person", "Location"};
        final EditText value = new EditText(this);
        value.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(value);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //option for type and then textfield for value of the tag
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void remTag(View view) {
        currPhoto.tagList.remove(selectedTag);
        try {
            Albums.writeApp(albums, fileName);
            albums = (Albums)Albums.readApp(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void remove(View view) {
        currAlbum.photoList.remove(currPhotoIndex);
        try {
            Albums.writeApp(albums, fileName);
            albums = (Albums)Albums.readApp(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void savePos(int pos) {
        selectedTag = pos;
    }



}