package com.example.photoslucas;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {


    private static final long serialVersionUID = 1L;

    public String path;
    public ArrayList<Tag> tagList;


    public Photo(String path){
        this.path = path;
        this.tagList = new ArrayList<Tag>();
    }

    public void addTag(Tag newTag){
        this.tagList.add(newTag);
    }

    public void removeTag(int index){
        this.tagList.remove(index);
    }

    public Uri getImage(){
        Uri image = null;
        image = Uri.parse(this.path);
        return image;
    }
}
