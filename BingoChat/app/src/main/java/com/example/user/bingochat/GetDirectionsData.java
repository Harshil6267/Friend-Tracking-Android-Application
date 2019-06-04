package com.example.user.bingochat;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

    /**
     * Created by Aditya on 3/17/2018.
     */

    public class GetDirectionsData extends AsyncTask<Object,String,String> {
        private GoogleMap mMap;
        private String googleDirectionsData;
        private LatLng latLng;

        @Override
        protected String doInBackground(Object... objects) {
            mMap = (GoogleMap)objects[0];
            String url = (String) objects[1];
            latLng=(LatLng)objects[2];

            DownloadURL downloadURL = new DownloadURL();
            try {
                googleDirectionsData = downloadURL.readUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return googleDirectionsData;
        }
        @Override
        protected void onPostExecute(String s){

            HashMap<String,String> directionsList=null;
            DataParser parser=new DataParser();
            directionsList=parser.parseDirections(s);
            String duration = directionsList.get("duration");
            String distance = directionsList.get("distance");

            mMap.clear();
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.draggable(true);
            markerOptions.title("Duration="+ duration);
            markerOptions.snippet("Distance="+ distance);
            mMap.addMarker(markerOptions);



        }
    }


