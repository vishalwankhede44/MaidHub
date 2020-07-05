package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewRequest extends AppCompatActivity implements OnItemSelectedListener {

    String name,service,mob,address,charge,duration,user_id;
    int h_id;
    Spinner hh_item,mm_item;
    EditText name_t,service_t,mob_t,address_t,charge_t;
    Button request,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);

        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        user_id=sp1.getString("id", null);
        h_id = Integer.parseInt(user_id);
        Spinner hh = (Spinner) findViewById(R.id.hh);
        Spinner mm = (Spinner) findViewById(R.id.mm);
        hh.setOnItemSelectedListener(this);
        mm.setOnItemSelectedListener(this);



        List<String> hrs = new ArrayList<String>();
        List<String> mins = new ArrayList<String>();
        for(int i=0;i<=12;i++) {
            hrs.add("    "+i+ "    ");
        }
        for(int i=0;i<=59;i++) {
            mins.add("    "+i+"    ");
        }

        // Creating adapter for
        ArrayAdapter<String> hhAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hrs);
        ArrayAdapter<String> minAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mins);

        // Drop down layout style - list view with radio button
        hhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        hh.setAdapter(hhAdapter);
        mm.setAdapter(minAdapter);


        request =(Button) findViewById(R.id.req_button);
        reset =(Button) findViewById(R.id.reset_button);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    name_t = findViewById(R.id.name_text);
                    name = name_t.getText().toString();
                    service_t = findViewById(R.id.service_text);
                    service = service_t.getText().toString();
                    mob_t = findViewById(R.id.mob_text);
                    mob = mob_t.getText().toString();
                    address_t = findViewById(R.id.add_text);
                    address = address_t.getText().toString();
                    charge_t = findViewById(R.id.charge_text);
                    charge = charge_t.getText().toString();
                    double charge_amt = Double.parseDouble(charge);
                    hh_item = findViewById(R.id.hh);
                    mm_item = findViewById(R.id.mm);
                    duration = hh_item.getSelectedItem().toString().trim() + " hr " + mm_item.getSelectedItem().toString().trim() + " min";

                    mydatabase.execSQL("INSERT INTO Requests VALUES(null,"+h_id+",'" + name + "','" + service + "','" + mob + "','" + address + "'," + charge_amt + ",'" + duration + "','NoOne','Pending');");
                    Toast.makeText(NewRequest.this, "Request Posted! ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NewRequest.this,HouseHome.class);
                    startActivity(i);
                }
                catch (Exception exc)
                {
                    Log.i("ERROR",exc.toString());
                    Toast.makeText(NewRequest.this, exc.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
