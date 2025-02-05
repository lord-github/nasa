package com.bbstudios.hks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sAdapter extends RecyclerView.Adapter<sHolder> {
    List<sanawucin> sanawucins;
    Context context;
    public sAdapter(List<sanawucin> sanawucins, Context context) {
        this.sanawucins = sanawucins;
        this.context = context;
    }

    @NonNull
    @Override
    public sHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new sHolder(LayoutInflater.from(context).inflate(R.layout.yekeback,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull sHolder holder, int position) {
        holder.zagtext.setText(sanawucins.get(position).zaga);
        if (sanawucins.get(position).zaga.length()<2){
            holder.button.setVisibility(View.INVISIBLE);
            holder.gorkez.setVisibility(View.INVISIBLE);
        }
        holder.zagtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sanawucins.get(position).zaga.length()>2){
                if (holder.expandableLayout.getVisibility()==View.GONE){
                    holder.expandableLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.expandableLayout.setVisibility(View.GONE);
                }
                }
            }
        });

        holder.duzumtext.setText(sanawucins.get(position).duzum);
        holder.gorkez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sanawucins.get(position).zaga.length()>2){
                    if (holder.expandableLayout.getVisibility()==View.GONE){
                        holder.expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        holder.expandableLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okla2(sanawucins.get(position).id);
            }
        });

    }

    private void okla2(String valueOf) {
        Intent intent=new Intent("custom-id");
        intent.putExtra("id",valueOf);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return sanawucins.size();
    }
}
