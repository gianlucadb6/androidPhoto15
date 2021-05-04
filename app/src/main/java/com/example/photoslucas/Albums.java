package com.example.photoslucas;

import java.io.*;
import java.util.ArrayList;
import android.content.Context;

public class Albums implements Serializable {

    private static final long serialVersionUID = 1L;

    ArrayList<Album> albumList;

    public Albums(){
        albumList = new ArrayList<Album>();
    }

    public static void writeApp(Albums albums, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(albums);
        os.close();
        fos.close();
    }

    public static Albums readApp(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis  = new FileInputStream(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Albums albums = (Albums) is.readObject();
        is.close();
        fis.close();
        return albums;
    }

    public Album exists(String album) {
        for(Album a: this.albumList) {
            if(a.name.equals(album)) {
                return a;
            }
        }
        return null;
    }
}
