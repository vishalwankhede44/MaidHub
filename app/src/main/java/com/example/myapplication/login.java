package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button login_b;
    RadioGroup rg;
    RadioButton loginas;
    TextView semail,spass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);

        login_b = findViewById(R.id.loginBtn);
        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               email = findViewById(R.id.email);
                pass = findViewById(R.id.password);
                rg= findViewById(R.id.loginas);
                String loginasStr = "Service Provider";
                if(email.getText().toString().trim().equals("")||pass.getText().toString().trim().equals(""))
                {
                    Toast.makeText(login.this,"Invalid Values",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int selectedID = rg.getCheckedRadioButtonId();
                    loginas = (RadioButton) findViewById(selectedID);
                    if(loginas.getText().toString().equals("House Holder   "))
                        loginasStr = "House Holder";

                    if (loginasStr.equals("Service Provider")) {
                        Cursor resultSet = mydatabase.rawQuery("Select * from ServiceProvider WHERE Email='"+ email.getText().toString().trim() +"'", null);
                        resultSet.moveToFirst();

                        if(resultSet.getCount()>0)
                        {
                            if(resultSet.getString(3).equals(pass.getText().toString())) {
                                int id = resultSet.getInt(0);
                                SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor Ed=sp.edit();
                                Ed.putString("id",id+"" );
                                Ed.putString("type","Service");
                                Ed.commit();
                                Intent i = new Intent(login.this,ProviderHome.class);
                                startActivity(i);
//                                Toast.makeText(login.this, "Welcome " + full_name + " , id: " + id, Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(login.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(login.this,"User Not Registered!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Cursor resultSet = mydatabase.rawQuery("Select * from HouseHolder WHERE Email='"+ email.getText().toString().trim() +"'", null);
                        resultSet.moveToFirst();

                        if(resultSet.getCount()>0)
                        {
                            if(resultSet.getString(3).equals(pass.getText().toString())) {

                                int id = resultSet.getInt(0);

                                SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor Ed=sp.edit();
                                Ed.putString("id",id+"" );
                                Ed.putString("type","House");
                                Ed.commit();
                                Intent i = new Intent(login.this,HouseHome.class);
                                startActivity(i);
//                                Toast.makeText(login.this, "Welcome " + full_name + " , id: " + id, Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(login.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(login.this,"User Not Registered!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
