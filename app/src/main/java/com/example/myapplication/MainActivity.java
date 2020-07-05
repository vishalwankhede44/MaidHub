package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button logbtn,Regbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String user_id=sp1.getString("id", null);
        String type = sp1.getString("type",null);
        Log.i("Start","Main");

        if(user_id!=null ||type!= null)
        {
            if(type.equals("Service"))
            {
                Log.i("Start","In Service");

                Intent i = new Intent(MainActivity.this,ProviderHome.class);
                startActivity(i);
            }
            else
            {
                Log.i("Start","In Home");

                Intent i = new Intent(MainActivity.this,HouseHome.class);
                startActivity(i);
            }
        }


        logbtn = (Button)findViewById(R.id.login_button);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,login.class);
                startActivity(i);
         }
        });

        Regbtn = (Button)findViewById(R.id.register_button);
        Regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });

        SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS ServiceProvider(id INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR,Email VARCHAR UNIQUE,Password VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS HouseHolder(id INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR,Email VARCHAR UNIQUE,Password VARCHAR);");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Requests(id INTEGER PRIMARY KEY AUTOINCREMENT,h_id INTEGER,Name VARCHAR,Service VARCHAR,Mobile VARCHAR,Address VARCHAR,Charge DOUBLE,Duration VARCHAR,S_id VARCHAR,Status VARCHAR);");
//        mydatabase.execSQL("DROP TABLE Requests");
//        mydatabase.execSQL("DELETE from HouseHolder");
//        mydatabase.execSQL("DELETE from ServiceProvider");

//        mydatabase.execSQL("INSERT INTO ServiceProvider VALUES(null,'admin','admin');");
//        mydatabase.execSQL("INSERT INTO HouseHolder VALUES(null,'admin1','admin1');");

//        Cursor resultSet = mydatabase.rawQuery("Select * from User",null);
//        resultSet.moveToFirst();
//        String username = resultSet.getString(0);
//        String password = resultSet.getString(1);
//        Log.i("USERNAME",username);
//        Log.i("PASSWORD",password);

    }
}
