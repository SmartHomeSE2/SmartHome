package com.smarthome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ManageItemsAdapter extends RecyclerView.Adapter<ManageItemsAdapter.MyViewHolder> {
    @NonNull
    @Override
    public ManageItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device, viewGroup, false);
        return new ManageItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageItemsAdapter.MyViewHolder myViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView image_item = itemView.findViewById(R.id.icon_item);
            TextView text_item = itemView.findViewById(R.id.text_item);
            Switch switch_item = itemView.findViewById(R.id.switch_item);
        }
    }
}
