package com.example.photoslucas;

import java.io.*;
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    String value;
    String type;

    public Tag(String type, String value){
        this.value = value;
        this.type = type;
    }

    public String toString(){
        return this.type + ": "+ this.value;
    }
}
