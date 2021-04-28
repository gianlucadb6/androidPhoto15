package com.example.photoslucas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

class Album{

    String name;
    //ArrayList<photo>


    public Album(String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

}


public class Albums extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Album> albumList;

    public static final int ALBUM_EDIT_CODE = 1;
    public static final int RESULT_DELETE = RESULT_FIRST_USER +1 ;
    public static final int RESULT_OPEN = RESULT_FIRST_USER + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list);


        //Fills out Albums Array List
        String[] aList = getResources().getStringArray(R.array.album_array);
        albumList = new ArrayList<>(aList.length);
        for (int i = 0; i < aList.length; i++) {
            albumList.add(new Album(aList[i]));

        }

    listView = findViewById(R.id.album_list);
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.album, albumList));

    // show movie for possible edit when tapped
        listView.setOnItemClickListener((p, V, pos, id) ->
                showAlbum(pos));

    }
    private void showAlbum(int pos) {
        Bundle bundle = new Bundle();
        Album album = albumList.get(pos);
        bundle.putInt(Edit_display_album.ALBUM_INDEX, pos);
        bundle.putString(Edit_display_album.ALBUM_NAME, album.name);
        Intent intent = new Intent(this, Edit_display_album.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ALBUM_EDIT_CODE);
    }

    private void openAlbum(int pos) {
        Bundle bundle = new Bundle();
        Album album = albumList.get(pos);
        bundle.putInt(Edit_display_album.ALBUM_INDEX, pos);
        bundle.putString(Edit_display_album.ALBUM_NAME, album.name);
        Intent intent = new Intent(this, open_album.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ALBUM_EDIT_CODE);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        // gather all info passed back by launched activity
        String name = bundle.getString(Edit_display_album.ALBUM_NAME);
        int index = bundle.getInt(Edit_display_album.ALBUM_INDEX);

        //Rename
        if (resultCode == RESULT_OK) {
            Album album = albumList.get(index);
            album.name = name;
           // albumList.remove(index);
        }
        //Delete
        else if(resultCode == RESULT_DELETE){
            albumList.remove(index);
        }
        //Open
        else if(resultCode == RESULT_OPEN){
            Album album = albumList.get(index);
            //Here is where you open album

        }

        // redo the adapter to reflect change^K
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.album, albumList));
    }

}