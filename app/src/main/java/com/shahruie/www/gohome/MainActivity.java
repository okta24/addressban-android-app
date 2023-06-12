package com.shahruie.www.gohome;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHandler db;
    byte[] inputData;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        Button btnadd = (Button) toolbarTop.findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(MainActivity.this,Add.class);
                startActivity(newActivity);
            }
        });
        Button btnabout = (Button) toolbarTop.findViewById(R.id.btnabout);
         db = new DBHandler(this);
           // db.Reset();
        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(RecyclerViewLayoutManager);


       /* ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
//int [] bytes=[2,1];
        Log.d("Insert: kobra", "Inserting  ..");
        //db.updateaddress(new Addresses(14," kobra","Dgfh","23127", "United", "States"));
        db.addaddress(new Addresses(2," Brannan St #330 ","Dockers","23127", "","hk"));
       // db.addaddress(new Addresses(3," 34475 Brannan St #330 San Francisco","Dockers","23127", "United", "States"));
       // db.addaddress(new Addresses(4," 47345 Brannan St #330 San Francisco","Dockers","23127", "United", "States"));
/*
      / Addresses one= db.getaddress(16);

        String log2 ="Id: " + one.getId() + " ,Name: " + one.getName() + " ,Address: " + one.getAddress()
                + " ,phone: " + one.getPhone() + " ,voice: " + one.getVoice()
                + " ,img: " + one.getLocimg();

        Log.d("loc: : ", log2);
        //db.deleteaddress(16);*/
        Log.d("Reading: ", "Reading all locs..");

        List<Addresses> locs = db.getAlllocs();
        for (Addresses loc : locs) {
            String log ="Id: " + loc.getId() + " ,Name: " + loc.getName() + " ,Address: " + loc.getAddress()
                    + " ,phone: " + loc.getPhone() + " ,voice: " + loc.getVoice();


            Log.d("loc: : ", log);

        }
       // Log.d("count: : ", db.getaddressCount()+"");

    }
    private void setdefulte(){
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.prof);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        inputData = stream.toByteArray();

    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Addresses> locs = db.getAlllocs();
        RecyclerView.Adapter adapter = new RecyclerViewAdapter(MainActivity.this,locs);
        recyclerview.setAdapter(adapter);
    }


}
