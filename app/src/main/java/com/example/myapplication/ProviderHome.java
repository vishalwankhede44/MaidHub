package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProviderHome extends AppCompatActivity {
    String name,service,charge,add,dur,mobile;
    TextView name_t,serv_t,charge_t,add_t,dur_t,mob_t,reqId_t,hid_t;
    int reqId,hid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);
        Log.i("Start","Started");
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String user_id=sp1.getString("id", null);
        Button logout=findViewById(R.id.logoutP);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
//                finish();
                Intent i = new Intent(ProviderHome.this,MainActivity.class);
                startActivity(i);
            }
        });
        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);
        Cursor resultSet = mydatabase.rawQuery("SELECT * FROM Requests WHERE s_id="+user_id+" AND status='Pending'",null);
        resultSet.moveToFirst();

        if(resultSet.getCount()>0)
        {
            reqId = resultSet.getInt(0);
            hid = resultSet.getInt(1);
            name = resultSet.getString(2);
            service = resultSet.getString(3);
            mobile = resultSet.getString(4);
            add =resultSet.getString(5);
            charge = resultSet.getDouble(6)+"";
            dur = resultSet.getString(7);

            name_t = findViewById(R.id.name_view);
            serv_t = findViewById(R.id.service_view);
            hid_t = findViewById(R.id.pid_view);
            add_t = findViewById(R.id.add_view);
            dur_t = findViewById(R.id.dur_view);
            charge_t = findViewById(R.id.charge_view);
            reqId_t = findViewById(R.id.ReqID);
            mob_t = findViewById(R.id.contact);


            reqId_t.setText(reqId+"");
            hid_t.setText(hid+"");
            name_t.setText(name+"");
            serv_t.setText(service+"");
            dur_t.setText(dur+"");
            mob_t.setText(mobile+"");
            charge_t.setText(charge+"");
            add_t.setText(add+"");

            Button complete =findViewById(R.id.complete);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ProviderHome.this,CollectAmt.class);
                    i.putExtra("INR",charge);
                    int id = reqId;
                    mydatabase.execSQL("UPDATE Requests SET Status='Complete' WHERE id="+id);
                    startActivity(i);
                }
            });

        }
        else
        {
            Intent i = new Intent(ProviderHome.this,ViewRequest.class);
            startActivity(i);
        }
    }
    @Override
    public void onBackPressed() {
        Log.i("Back","Back Pressed");
    }

}
