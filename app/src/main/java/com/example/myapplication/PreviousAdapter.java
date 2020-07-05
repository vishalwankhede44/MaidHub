package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;




public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.PreviousViewHolder> {

    Vector  names, service, address, hids, charges, durs, statuses;


   PreviousRequests vr;
    SQLiteDatabase mydatabase;

    public PreviousAdapter(Vector names, Vector service, Vector address, Vector hids, Vector charges, Vector durs,PreviousRequests vr,SQLiteDatabase mydb,Vector statuses) {

        this.names = names;
        this.service = service;
        this.address = address;
        this.hids = hids;
        this.charges = charges;
        this.durs = durs;

        this.vr = vr;
        mydatabase= mydb;
        this.statuses = statuses;


    }

    @NonNull
    @Override
    public PreviousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.prev_requests_view, parent, false);
        return new PreviousViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousViewHolder holder, int position) {

    try {
        holder.name_view.setText(names.get(position).toString());
        holder.service_view.setText(service.get(position).toString());
        holder.add_view.setText(address.get(position).toString());
        holder.hid_view.setText(hids.get(position).toString());
        holder.charge_view.setText(charges.get(position).toString());
        holder.dur_view.setText(durs.get(position).toString());
        holder.status.setText(statuses.get(position).toString());

        Log.i("VALUES", names.get(position).toString());
        Log.i("VALUES", service.get(position).toString());
        Log.i("VALUES", address.get(position).toString());
        Log.i("VALUES", hids.get(position).toString());
        Log.i("VALUES", charges.get(position).toString());
        Log.i("VALUES", durs.get(position).toString());
        Log.i("VALUES", statuses.get(position).toString());
    }
    catch (Exception excp)
    {
        Log.i("EXCEP",excp.toString());
    }

    }


    @Override
    public int getItemCount() {
        Log.i("SIZE",names.size()+"");
        return names.size();

    }

    public class PreviousViewHolder extends RecyclerView.ViewHolder {
        public TextView name_view, service_view, add_view, hid_view, charge_view, dur_view,status;
        public PreviousViewHolder(@NonNull final View itemView) {
            super(itemView);
            name_view = itemView.findViewById(R.id.name_view);
            service_view = itemView.findViewById(R.id.service_view);
            add_view = itemView.findViewById(R.id.add_view);
            hid_view = itemView.findViewById(R.id.pid_view);
            charge_view = itemView.findViewById(R.id.charge_view);
            dur_view = itemView.findViewById(R.id.dur_view);
            status = itemView.findViewById(R.id.Status);


        }
    }
}