package com.smarthome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ManageItemsAdapter extends RecyclerView.Adapter<ManageItemsAdapter.MyViewHolder> {

    private OnItemClickListener mItemClickListener;

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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView image_item = itemView.findViewById(R.id.icon_item);
            TextView name_item = itemView.findViewById(R.id.name_item);
            Switch switch_item = itemView.findViewById(R.id.switch_item);
            switch_item.setOnClickListener(this);
            /*switch_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        // Add view
                    }else {
                        // Remove view
                    }
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListner(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
