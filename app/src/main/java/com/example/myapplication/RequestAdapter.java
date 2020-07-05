package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Vector ids, names, service, address, hids, charges, durs, mobs;

    int pos;
    ViewRequest vr;
     SQLiteDatabase mydatabase;

    public RequestAdapter(Vector ids, Vector names, Vector service, Vector address, Vector hids, Vector charges, Vector durs, Vector mobs,ViewRequest vr,SQLiteDatabase mydb) {
        this.ids = ids;
        this.names = names;
        this.service = service;
        this.address = address;
        this.hids = hids;
        this.charges = charges;
        this.durs = durs;
        this.mobs = mobs;
        this.vr = vr;
        mydatabase= mydb;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_request_view, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        holder.name_view.setText(names.get(position) + "");
        holder.service_view.setText(service.get(position) + "");
        holder.add_view.setText(address.get(position) + "");
        holder.hid_view.setText(hids.get(position) + "");
        holder.charge_view.setText(charges.get(position) + "");
        holder.dur_view.setText(durs.get(position) + "");
        holder.callTxt.setText(mobs.get(position)+"");

    }


    @Override
    public int getItemCount() {

        return ids.size();

    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView name_view, service_view, add_view, hid_view, charge_view, dur_view,callTxt;
        Button BookBtn;

        public RequestViewHolder(@NonNull final View itemView) {
            super(itemView);
            callTxt = itemView.findViewById(R.id.provider_call);
            BookBtn = itemView.findViewById(R.id.book_btn);
            name_view = itemView.findViewById(R.id.name_view);
            service_view = itemView.findViewById(R.id.service_view);
            add_view = itemView.findViewById(R.id.add_view);
            hid_view = itemView.findViewById(R.id.pid_view);
            charge_view = itemView.findViewById(R.id.charge_view);
            dur_view = itemView.findViewById(R.id.dur_view);


            BookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    SharedPreferences sp1=vr.getSharedPreferences("Login", MODE_PRIVATE);
                    String user_id=sp1.getString("id", null);
                    int id = Integer.parseInt(ids.get(pos).toString());
                    mydatabase.execSQL("UPDATE Requests SET S_id='"+user_id+"' WHERE id="+id+" ");
                    Toast.makeText(vr,"REQUEST APPROVED", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(vr,ProviderHome.class);
                    vr.startActivity(i);
                    Log.i("Call",mobs.get(pos).toString());
                }
            });
        }
    }
}
