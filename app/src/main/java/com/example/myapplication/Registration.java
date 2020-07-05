package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText f_name;
    EditText email;
    EditText pass;
    Button register_b;
    RadioGroup rg;
    RadioButton regas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);
        register_b = findViewById(R.id.RegBtn);
        register_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_name = findViewById(R.id.fullname);
                email = findViewById(R.id.email);
                pass = findViewById(R.id.password);
                rg= findViewById(R.id.registeras);
                String RegasStr = "Service Provider";
                if(f_name.getText().toString().equals("")||pass.getText().toString().equals("")||email.getText().toString().equals(""))
                {
                    Toast.makeText(Registration.this,"Invalid Values",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name= f_name.getText().toString();
                    String emailid = email.getText().toString();
                    String pwd = pass.getText().toString();
                    int selectedID = rg.getCheckedRadioButtonId();
                    regas = (RadioButton) findViewById(selectedID);
                    if(regas.getText().toString().equals("House Holder   "))
                        RegasStr = "House Holder";
                 try {
                     if (RegasStr.equals("Service Provider")) {
                         mydatabase.execSQL("INSERT INTO ServiceProvider VALUES(null,'" + name + "','" + emailid + "','" + pwd + "');");
                     } else {
                         mydatabase.execSQL("INSERT INTO HouseHolder VALUES(null,'" + name + "','" + emailid + "','" + pwd + "');");
                     }

                     Toast.makeText(Registration.this, "Registered " + RegasStr, Toast.LENGTH_SHORT).show();
                     Intent i = new Intent(Registration.this,login.class);
                     startActivity(i);
                    }
                 catch (android.database.sqlite.SQLiteException sqex)
                 {
                     Toast.makeText(Registration.this, "Exception Occured : "+sqex.toString(), Toast.LENGTH_SHORT).show();
                 }
                }
            }
        });
    }
}
