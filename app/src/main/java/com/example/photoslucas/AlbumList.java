package com.example.photoslucas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;




public class AlbumList extends AppCompatActivity {


    private ListView listView;
    private ArrayList<Album> albumList;
    private Albums albums;
    final Context context = this;
    String fileName;
    public static final int ALBUM_EDIT_CODE = 1;
    public static final int RESULT_DELETE = RESULT_FIRST_USER +1 ;
    public static final int RESULT_OPEN = RESULT_FIRST_USER + 2;
    public static final int RESULT_ADD = RESULT_FIRST_USER + 3;
    public static final int RESULT_SEARCH = RESULT_FIRST_USER + 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list);


     /*   //Fills out Albums Array List
        String[] aList = getResources().getStringArray(R.array.album_array);
        albumList = new ArrayList<>(aList.length);
        for (int i = 0; i < aList.length; i++) {
            albumList.add(new Album(aList[i]));

        }

*/
        String fileName = this.getApplicationInfo().dataDir + "/albums.dat";

        try {
            albums = Albums.readApp(fileName);
            Toast.makeText(AlbumList.this, "1", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(AlbumList.this, "2", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Toast.makeText(AlbumList.this, "3", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if(albums == null){
            albums = new Albums();
        }

        listView = findViewById(R.id.album_list);
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.album, albums.albumList));

    // show movie for possible edit when tapped
        listView.setOnItemClickListener((p, V, pos, id) ->
                showAlbum(pos));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                   addAlbum();
                return true;
            case R.id.action_search:
                searchOptions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchOptions(){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, SearchOptions.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ALBUM_EDIT_CODE);
    }

    private void addAlbum(){
        Bundle bundle = new Bundle();
        bundle.putString(Edit_display_album.ALBUM_NAME, " ");
        Intent intent = new Intent(this, Add_album.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ALBUM_EDIT_CODE);
    }
    private void showAlbum(int pos) {
        Bundle bundle = new Bundle();
        Album album = albums.albumList.get(pos);
        bundle.putInt(Edit_display_album.ALBUM_INDEX, pos);
        bundle.putString(Edit_display_album.ALBUM_NAME, album.name);
        Intent intent = new Intent(this, Edit_display_album.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ALBUM_EDIT_CODE);
    }

    private void openAlbum(int pos) {
        Bundle bundle = new Bundle();
        Album album = albums.albumList.get(pos);
        bundle.putInt(open_album.ALBUM_INDEX, pos);
        bundle.putString(open_album.ALBUM_NAME, album.name);
        bundle.putSerializable("albums", albums);
        Intent intent = new Intent(this, open_album.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

         fileName = this.getApplicationInfo().dataDir + "/albums.dat";

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        // gather all info passed back by launched activity
        String name = " ";
        int index = 0;
        if(resultCode == RESULT_OK || resultCode == RESULT_DELETE ||resultCode == RESULT_OPEN ) {
             name = bundle.getString(Edit_display_album.ALBUM_NAME);
             index = bundle.getInt(Edit_display_album.ALBUM_INDEX);
        }else if(resultCode == RESULT_ADD){
            name = bundle.getString(Add_album.ALBUM_NAME);
        }

        //Rename
        if (resultCode == RESULT_OK) {
            Album album = albums.albumList.get(index);
            album.name = name;
            try {
                Albums.writeApp(albums, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
           // albumList.remove(index);
        }
        //Delete
        else if(resultCode == RESULT_DELETE){
            albums.albumList.remove(index);
            try {
                Albums.writeApp(albums, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Open
        else if(resultCode == RESULT_OPEN){
            Album album = albums.albumList.get(index);
            //Here is where you open album
           openAlbum(index);
        }
        //Add
        else if(resultCode == RESULT_ADD){
            albums.albumList.add(new Album(name));
            try {
                Albums.writeApp(albums, fileName);
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
        // redo the adapter to reflect change^K
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.album, albums.albumList));
    }

}