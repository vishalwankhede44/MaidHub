package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CollectAmt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_amt);
        Intent i =getIntent();
        String inr =i.getStringExtra("INR");
        inr = inr+" Rs.";
        TextView in = findViewById(R.id.inr);
        in.setText(inr);

        Button c = findViewById(R.id.collect);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CollectAmt.this,ViewRequest.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.i("Back","Back Pressed");
    }
}
