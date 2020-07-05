package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;

import java.util.Vector;

public class PreviousRequests extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_requests);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_prev_request);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        int user_id=Integer.parseInt(sp1.getString("id", null));

        try {
            Cursor resultSet = mydatabase.rawQuery("Select * from Requests WHERE h_id=" + user_id + " ORDER BY id DESC;", null);
            resultSet.moveToFirst();

            int count = resultSet.getCount();
            int i = 0;
            if(count==0)
            {
                Intent in = new Intent(PreviousRequests.this,NoPrev.class);
                startActivity(in);
            }
            else {
                Vector names = new Vector();
                Vector service = new Vector();
                Vector address = new Vector();
                Vector pids = new Vector();
                Vector charges = new Vector();
                Vector durs = new Vector();
                Vector statuses = new Vector();

                while (i < count) {
                    Log.i("DEBUG", "IN IF");

                    names.add(resultSet.getString(2));
                    service.add(resultSet.getString(3));
                    address.add(resultSet.getString(5));

                    charges.add(resultSet.getDouble(6) + "");
                    durs.add(resultSet.getString(7));

                    String intid = resultSet.getString(8);
                    statuses.add(resultSet.getString(9));

                    try {
                        if(!intid.equals("NoOne")) {
                            Log.i("IDD", "Select * from ServiceProvider WHERE id=" + intid);
                            Cursor nameSet = mydatabase.rawQuery("Select * from ServiceProvider WHERE id=" + intid, null);
                            Log.i("IDD", "Count - " + nameSet.getCount());
                            nameSet.moveToFirst();
                            names.add(nameSet.getString(1));
                            pids.add(intid);
                        }
                        else
                        {
                            names.add("Not Assigned Yet");
                            pids.add(intid);
                        }

                    } catch (SQLiteException ex) {
                        Log.i("ERRORS", ex.toString());
                    }

                    i++;

                    resultSet.moveToNext();
                }


                mAdapter = new PreviousAdapter(names, service, address, pids, charges, durs, PreviousRequests.this, mydatabase, statuses);
                recyclerView.setAdapter(mAdapter);
            }
        }
        catch (SQLiteException sexce)
        {
            Intent i = new Intent(PreviousRequests.this,NoPrev.class);
            startActivity(i);
        }
    }
}
