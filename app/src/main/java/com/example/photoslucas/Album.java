package com.example.photoslucas;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    ArrayList<Photo> photoList;


    public Album(String name){
        this.name = name;
        photoList = new ArrayList<Photo>();
    }

    public String toString(){
            return this.name;
        }


}
