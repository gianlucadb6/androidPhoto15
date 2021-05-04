package com.example.photoslucas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.net.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.example.photoslucas.R.layout.open_album_display;

public class PhotoListAdapter extends ArrayAdapter <Photo> {
    
    ArrayList photoList = new ArrayList<>();
    Context mContext;
    public PhotoListAdapter(Context context, ArrayList<Photo> photoList){
        super(context, open_album_display, photoList);
        this.photoList = photoList;
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = (View) inflater.inflate(R.layout.grid_image, parent, false);
       ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_image);

        Photo currPhoto = (Photo) photoList.get(position);
        Uri currUri = null;

        currUri = Uri.parse(currPhoto.path);

        imageView.setImageURI(currUri);
        return convertView;
    }
/*
    public class PhotoListAdapter extends BaseAdapter {


        private Context mContext;

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.spotify
        };

        // Constructor
        public PhotoListAdapter(Context c){
            mContext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            return imageView;
        }

 */
}

