package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView mylist;
    static ArrayList<String> places=new ArrayList<String>();
    static ArrayList<LatLng> locations=new ArrayList<LatLng>();
    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylist=(ListView)findViewById(R.id.list);
        SharedPreferences shared=this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);



        places.clear();

        ArrayList<String> lats=new ArrayList<>();
        ArrayList<String> longs=new ArrayList<>();
        lats.clear();
        longs.clear();
        locations.clear();
        try {
            places=(ArrayList<String>)ObjectSerializer.deserialize(shared.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            lats=(ArrayList<String>)ObjectSerializer.deserialize(shared.getString("latitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            longs=(ArrayList<String>)ObjectSerializer.deserialize(shared.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(places.size()>0 && lats.size()>0 && longs.size()>0)
        {
            for(int i=0;i<lats.size();i++)
            {
                LatLng ls=new LatLng(Double.parseDouble(lats.get(i)),Double.parseDouble(longs.get(i)));
                locations.add(ls);
            }
        }
        else
        {
            places.add("Add a new place");
            locations.add(new LatLng(0,0));
        }

        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        mylist.setAdapter(arrayAdapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Position:", String.valueOf(position));
                   Intent I=new Intent(getApplicationContext(),MapsActivity.class);
                   I.putExtra("placesno",position);
                   startActivity(I);

            }
        });


    }
}
