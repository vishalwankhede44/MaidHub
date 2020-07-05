package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;


import java.util.Vector;

public class ViewRequest extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_request);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final SQLiteDatabase mydatabase = openOrCreateDatabase("MaidHub",MODE_PRIVATE,null);
        try {
            Cursor resultSet = mydatabase.rawQuery("Select * from Requests WHERE s_id='NoOne'", null);
            resultSet.moveToFirst();
            int count = resultSet.getCount();
            int i = 0;
            Vector names = new Vector();
            Vector service = new Vector();
            Vector address = new Vector();
            Vector hids = new Vector();
            Vector charges = new Vector();
            Vector durs = new Vector();
            Vector ids = new Vector();
            Vector mobs = new Vector();
            if (count == 0) {
                Log.i("Start", "No Data Available");
                Intent in = new Intent(ViewRequest.this,NoDataFound.class);
                startActivity(in);
            }

            while (i < count) {
                names.add(resultSet.getString(2));
                service.add(resultSet.getString(3));
                address.add(resultSet.getString(5));
                hids.add(resultSet.getInt(1) + "");
                charges.add(resultSet.getDouble(6) + "");
                durs.add(resultSet.getString(7));
                ids.add(resultSet.getInt(0) + "");
                mobs.add(resultSet.getString(4));
                i++;

                resultSet.moveToNext();
            }

            mAdapter = new RequestAdapter(ids, names, service, address, hids, charges, durs, mobs, ViewRequest.this, mydatabase);
            recyclerView.setAdapter(mAdapter);
        }
        catch (SQLiteException secp)
        {
            Intent i = new Intent(ViewRequest.this,NoDataFound.class);
            startActivity(i);
        }


    }
    @Override
    public void onBackPressed() {
        Log.i("Back","Back Pressed");
    }
}
